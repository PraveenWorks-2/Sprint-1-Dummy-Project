package com.oneenterprise.dummyproject.platform.configuration.repository;

import com.oneenterprise.dummyproject.platform.configuration.entity.PlatformConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformConfigRepository extends JpaRepository<PlatformConfig, Long> {
    Optional<PlatformConfig> findByConfigKey(String configKey);
    boolean existsByConfigKey(String configKey);
}