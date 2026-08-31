package com.oneenterprise.tenant.repository;

import com.oneenterprise.tenant.entity.TenantConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TenantConfigurationRepository extends JpaRepository<TenantConfiguration, UUID> {
    Optional<TenantConfiguration> findByTenantId(UUID tenantId);
}
