package com.oneenterprise.notificationservice.dto;

import com.oneenterprise.notificationservice.entity.NotificationChannel;
import com.oneenterprise.notificationservice.entity.NotificationStatus;
import com.oneenterprise.notificationservice.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {

    private Long id;
    private Long recipientUserId;
    private Long tenantId;
    private String title;
    private String message;
    private NotificationType notificationType;
    private NotificationChannel channel;
    private NotificationStatus status;
    private String sourceEvent;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
