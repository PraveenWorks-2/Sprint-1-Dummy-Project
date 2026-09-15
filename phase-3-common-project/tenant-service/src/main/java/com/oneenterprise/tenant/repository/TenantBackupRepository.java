package com.oneenterprise.tenant.repository;
import com.oneenterprise.tenant.entity.TenantBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
public interface TenantBackupRepository extends JpaRepository<TenantBackup, UUID> { List<TenantBackup> findByTenantIdOrderByCreatedAtDesc(UUID tenantId); }
