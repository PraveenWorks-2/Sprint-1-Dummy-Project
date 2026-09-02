package com.enterprise.auditservice.kafka;

import com.enterprise.auditservice.dto.request.AuditLogRequest;
import com.enterprise.auditservice.enums.AuditAction;
import com.enterprise.auditservice.service.AuditLogService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuditEventListener {

    private static final Logger log = LoggerFactory.getLogger(AuditEventListener.class);

    private final AuditLogService auditLogService;
    private final Validator validator;

    public AuditEventListener(AuditLogService auditLogService, Validator validator) {
        this.auditLogService = auditLogService;
        this.validator = validator;
    }

    @KafkaListener(topics = "${audit.kafka.topics.role-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeRoleEvent(AuditEventMessage event) {
        log.info("Received role event from source service: {}", event.getSourceService());
        persist(event);
    }

    @KafkaListener(topics = "${audit.kafka.topics.permission-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePermissionEvent(AuditEventMessage event) {
        log.info("Received permission event from source service: {}", event.getSourceService());
        persist(event);
    }

    @KafkaListener(topics = "${audit.kafka.topics.security-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeSecurityEvent(AuditEventMessage event) {
        log.info("Received security event from source service: {}", event.getSourceService());
        persist(event);
    }

    @KafkaListener(topics = "${audit.kafka.topics.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUserEvent(AuditEventMessage event) {
        log.info("Received user event from source service: {}", event.getSourceService());
        persist(event);
    }

    private void persist(AuditEventMessage event) {
        AuditAction action;
        try {
            action = AuditAction.valueOf(event.getAction());
        } catch (IllegalArgumentException | NullPointerException ex) {
            log.warn("Unrecognized action '{}' from {}, defaulting to OTHER",
                    event.getAction(), event.getSourceService());
            action = AuditAction.OTHER;
        }

        AuditLogRequest request = AuditLogRequest.builder()
                .userId(event.getUserId())
                .tenantId(event.getTenantId())
                .action(action)
                .module(event.getModule())
                .entityName(event.getEntityName())
                .entityId(event.getEntityId())
                .description(event.getDescription())
                .sourceService(event.getSourceService())
                .build();

        Set<ConstraintViolation<AuditLogRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(", "));
            log.error("Rejected invalid audit event from {}: {}", event.getSourceService(), errors);
            return;
        }

        auditLogService.createAuditLog(request);
    }
}