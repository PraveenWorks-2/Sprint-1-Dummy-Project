package com.enterprise.auditservice.service.impl;

import com.enterprise.auditservice.dto.request.AuditLogRequest;
import com.enterprise.auditservice.dto.response.AuditLogResponse;
import com.enterprise.auditservice.entity.AuditLog;
import com.enterprise.auditservice.enums.AuditAction;
import com.enterprise.auditservice.exception.AuditLogNotFoundException;
import com.enterprise.auditservice.repository.AuditLogRepository;
import com.enterprise.auditservice.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    public AuditLogResponse createAuditLog(AuditLogRequest request) {
        AuditLog auditLog = AuditLog.builder()
                .userId(request.getUserId())
                .tenantId(request.getTenantId())
                .action(request.getAction())
                .module(request.getModule())
                .entityName(request.getEntityName())
                .entityId(request.getEntityId())
                .description(request.getDescription())
                .ipAddress(request.getIpAddress())
                .sourceService(request.getSourceService())
                .createdAt(LocalDateTime.now())
                .build();

        AuditLog saved = auditLogRepository.save(auditLog);
        return mapToResponse(saved);
    }

    @Override
    public List<AuditLogResponse> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuditLogResponse getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new AuditLogNotFoundException(id));
        return mapToResponse(auditLog);
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByUserId(Long userId) {
        return auditLogRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByTenantId(Long tenantId) {
        return auditLogRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse> getAuditLogsByAction(AuditAction action) {
        return auditLogRepository.findByAction(action)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse> getAuditLogsBySourceService(String sourceService) {
        return auditLogRepository.findBySourceService(sourceService)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditLogResponse> getAuditTrailForEntity(String entityName, String entityId) {
        return auditLogRepository.findByEntityNameAndEntityId(entityName, entityId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AuditLogResponse mapToResponse(AuditLog auditLog) {
        return AuditLogResponse.builder()
                .id(auditLog.getId())
                .userId(auditLog.getUserId())
                .tenantId(auditLog.getTenantId())
                .action(auditLog.getAction())
                .module(auditLog.getModule())
                .entityName(auditLog.getEntityName())
                .entityId(auditLog.getEntityId())
                .description(auditLog.getDescription())
                .ipAddress(auditLog.getIpAddress())
                .sourceService(auditLog.getSourceService())
                .createdAt(auditLog.getCreatedAt())
                .build();
    }
}