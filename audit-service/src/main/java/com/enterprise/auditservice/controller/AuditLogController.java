package com.enterprise.auditservice.controller;

import com.enterprise.auditservice.dto.request.AuditLogRequest;
import com.enterprise.auditservice.dto.response.AuditLogResponse;
import com.enterprise.auditservice.enums.AuditAction;
import com.enterprise.auditservice.service.AuditLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    public ResponseEntity<AuditLogResponse> createAuditLog(@Valid @RequestBody AuditLogRequest request) {
        AuditLogResponse response = auditLogService.createAuditLog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> getAllAuditLogs() {
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponse> getAuditLogById(@PathVariable Long id) {
        return ResponseEntity.ok(auditLogService.getAuditLogById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByUserId(userId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByTenantId(tenantId));
    }

    @GetMapping("/action/{action}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsByAction(@PathVariable AuditAction action) {
        return ResponseEntity.ok(auditLogService.getAuditLogsByAction(action));
    }

    @GetMapping("/source/{sourceService}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogsBySourceService(@PathVariable String sourceService) {
        return ResponseEntity.ok(auditLogService.getAuditLogsBySourceService(sourceService));
    }

    @GetMapping("/entity/{entityName}/{entityId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditTrailForEntity(
            @PathVariable String entityName,
            @PathVariable String entityId) {
        return ResponseEntity.ok(auditLogService.getAuditTrailForEntity(entityName, entityId));
    }
}