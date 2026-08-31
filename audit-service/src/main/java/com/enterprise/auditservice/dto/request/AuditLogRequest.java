package com.enterprise.auditservice.dto.request;

import com.enterprise.auditservice.enums.AuditAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogRequest {

    private Long userId;

    private Long tenantId;

    @NotNull(message = "Action is required")
    private AuditAction action;

    @Size(max = 60, message = "Module must not exceed 60 characters")
    private String module;

    @Size(max = 100, message = "Entity name must not exceed 100 characters")
    private String entityName;

    private String entityId;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private String ipAddress;

    @NotBlank(message = "Source service is required")
    @Size(max = 60, message = "Source service must not exceed 60 characters")
    private String sourceService;
}