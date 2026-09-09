package com.oneenterprise.tenant.repository;
import com.oneenterprise.tenant.entity.TenantProvisioning;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
public interface TenantProvisioningRepository extends JpaRepository<TenantProvisioning, UUID> { Optional<TenantProvisioning> findByTenantId(UUID tenantId); }
