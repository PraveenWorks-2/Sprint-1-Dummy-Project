package com.enterprise.auditservice.kafka;

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
public class AuditEventMessage {

    private Long userId;
    private Long tenantId;
    private String action;
    private String module;
    private String entityName;
    private String entityId;
    private String description;
    private String sourceService;
}