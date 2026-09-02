package com.enterprise.auditservice.repository;

import com.enterprise.auditservice.entity.AuditLog;
import com.enterprise.auditservice.enums.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByTenantId(Long tenantId);

    List<AuditLog> findByAction(AuditAction action);

    List<AuditLog> findBySourceService(String sourceService);

    List<AuditLog> findByEntityNameAndEntityId(String entityName, String entityId);

    List<AuditLog> findByUserIdAndAction(Long userId, AuditAction action);
}