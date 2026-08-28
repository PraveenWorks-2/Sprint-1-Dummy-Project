package com.oneenterprise.notificationservice.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic event payload consumed from upstream services
 * (Role, Permission, User-Role, Security & Session services)
 * and also used when this service publishes notification-sent events
 * for the Audit & Activity Service to consume.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private Long userId;
    private Long tenantId;
    private String eventType;   // e.g. ROLE_ASSIGNED, PERMISSION_CHANGED, SECURITY_ALERT
    private String description;
    private String triggeredBy;
    private String timestamp;
}
