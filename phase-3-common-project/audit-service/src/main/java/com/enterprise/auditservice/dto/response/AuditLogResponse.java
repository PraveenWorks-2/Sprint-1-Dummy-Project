package com.enterprise.auditservice.dto.response;

import com.enterprise.auditservice.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponse {

    private Long id;
    private Long userId;
    private Long tenantId;
    private AuditAction action;
    private String module;
    private String entityName;
    private String entityId;
    private String description;
    private String ipAddress;
    private String sourceService;
    private LocalDateTime createdAt;
}