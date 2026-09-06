package com.oneenterprise.notificationservice.serviceimpl;

import com.oneenterprise.notificationservice.dto.NotificationRequestDto;
import com.oneenterprise.notificationservice.dto.NotificationResponseDto;
import com.oneenterprise.notificationservice.entity.Notification;
import com.oneenterprise.notificationservice.entity.NotificationStatus;
import com.oneenterprise.notificationservice.exception.ResourceNotFoundException;
import com.oneenterprise.notificationservice.kafka.NotificationEvent;
import com.oneenterprise.notificationservice.kafka.NotificationEventProducer;
import com.oneenterprise.notificationservice.repository.NotificationRepository;
import com.oneenterprise.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationEventProducer notificationEventProducer;

    @Override
    public NotificationResponseDto createNotification(NotificationRequestDto requestDto) {
        Notification notification = Notification.builder()
                .recipientUserId(requestDto.getRecipientUserId())
                .tenantId(requestDto.getTenantId())
                .title(requestDto.getTitle())
                .message(requestDto.getMessage())
                .notificationType(requestDto.getNotificationType())
                .channel(requestDto.getChannel())
                .sourceEvent(requestDto.getSourceEvent())
                .status(NotificationStatus.PENDING)
                .build();

        Notification saved = notificationRepository.save(notification);

        // Simulate dispatch based on channel (Email / In-App / Both)
        dispatch(saved);

        return mapToResponseDto(saved);
    }

    /**
     * Processes the notification for the configured channel(s).
     * In a production system this would call an email provider (SMTP/SES/SendGrid)
     * and/or push to an in-app notification store/websocket.
     */
    private void dispatch(Notification notification) {
        try {
            switch (notification.getChannel()) {
                case EMAIL -> log.info("Sending EMAIL notification to userId={} | title='{}'",
                        notification.getRecipientUserId(), notification.getTitle());
                case IN_APP -> log.info("Publishing IN_APP notification to userId={} | title='{}'",
                        notification.getRecipientUserId(), notification.getTitle());
                case BOTH -> log.info("Sending EMAIL + IN_APP notification to userId={} | title='{}'",
                        notification.getRecipientUserId(), notification.getTitle());
            }

            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);

            // Publish event so Audit & Activity Service can record it
            notificationEventProducer.publishNotificationSentEvent(
                    new NotificationEvent(
                            notification.getRecipientUserId(),
                            notification.getTenantId(),
                            "NOTIFICATION_SENT",
                            notification.getTitle(),
                            "notification-service",
                            LocalDateTime.now().toString()
                    )
            );
        } catch (Exception ex) {
            log.error("Failed to dispatch notification id={}", notification.getId(), ex);
            notification.setStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
        }
    }

    @Override
    public List<NotificationResponseDto> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return mapToResponseDto(notification);
    }

    @Override
    public List<NotificationResponseDto> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByRecipientUserId(userId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationResponseDto markAsSent(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        return mapToResponseDto(notificationRepository.save(notification));
    }

    @Override
    public NotificationResponseDto markAsFailed(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.FAILED);
        return mapToResponseDto(notificationRepository.save(notification));
    }

    @Override
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }

    private NotificationResponseDto mapToResponseDto(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .recipientUserId(notification.getRecipientUserId())
                .tenantId(notification.getTenantId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .notificationType(notification.getNotificationType())
                .channel(notification.getChannel())
                .status(notification.getStatus())
                .sourceEvent(notification.getSourceEvent())
                .createdAt(notification.getCreatedAt())
                .sentAt(notification.getSentAt())
                .build();
    }
}
