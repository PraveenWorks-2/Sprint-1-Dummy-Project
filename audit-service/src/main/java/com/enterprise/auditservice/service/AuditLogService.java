package com.enterprise.auditservice.service;

import com.enterprise.auditservice.dto.request.AuditLogRequest;
import com.enterprise.auditservice.dto.response.AuditLogResponse;
import com.enterprise.auditservice.enums.AuditAction;

import java.util.List;

public interface AuditLogService {

    AuditLogResponse createAuditLog(AuditLogRequest request);

    List<AuditLogResponse> getAllAuditLogs();

    AuditLogResponse getAuditLogById(Long id);

    List<AuditLogResponse> getAuditLogsByUserId(Long userId);

    List<AuditLogResponse> getAuditLogsByTenantId(Long tenantId);

    List<AuditLogResponse> getAuditLogsByAction(AuditAction action);

    List<AuditLogResponse> getAuditLogsBySourceService(String sourceService);

    List<AuditLogResponse> getAuditTrailForEntity(String entityName, String entityId);
}