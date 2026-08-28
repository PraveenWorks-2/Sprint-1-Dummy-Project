package com.oneenterprise.notificationservice.kafka;

import com.oneenterprise.notificationservice.dto.NotificationRequestDto;
import com.oneenterprise.notificationservice.entity.NotificationChannel;
import com.oneenterprise.notificationservice.entity.NotificationType;
import com.oneenterprise.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes events published by upstream services in the integration flow
 * (Role-Permission Service, User-Role Service, Security & Session Service)
 * and converts them into notifications.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${app.kafka.topic.role-assigned}", groupId = "notification-service-group")
    public void consumeRoleAssignedEvent(NotificationEvent event) {
        log.info("Received role-assigned event: {}", event);
        NotificationRequestDto dto = new NotificationRequestDto(
                event.getUserId(),
                event.getTenantId(),
                "Role Assigned",
                event.getDescription() != null ? event.getDescription() : "A new role has been assigned to your account.",
                NotificationType.ROLE_ASSIGNED,
                NotificationChannel.BOTH,
                event.getEventType()
        );
        notificationService.createNotification(dto);
    }

    @KafkaListener(topics = "${app.kafka.topic.permission-changed}", groupId = "notification-service-group")
    public void consumePermissionChangedEvent(NotificationEvent event) {
        log.info("Received permission-changed event: {}", event);
        NotificationRequestDto dto = new NotificationRequestDto(
                event.getUserId(),
                event.getTenantId(),
                "Permission Updated",
                event.getDescription() != null ? event.getDescription() : "Your permissions have been updated.",
                NotificationType.PERMISSION_CHANGED,
                NotificationChannel.IN_APP,
                event.getEventType()
        );
        notificationService.createNotification(dto);
    }

    @KafkaListener(topics = "${app.kafka.topic.security-activity}", groupId = "notification-service-group")
    public void consumeSecurityActivityEvent(NotificationEvent event) {
        log.info("Received security-activity event: {}", event);
        NotificationRequestDto dto = new NotificationRequestDto(
                event.getUserId(),
                event.getTenantId(),
                "Security Alert",
                event.getDescription() != null ? event.getDescription() : "New security activity detected on your account.",
                NotificationType.SECURITY_ALERT,
                NotificationChannel.EMAIL,
                event.getEventType()
        );
        notificationService.createNotification(dto);
    }
}
