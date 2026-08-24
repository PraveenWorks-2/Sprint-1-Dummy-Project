package com.oneenterprise.dummyproject.platform.branding.repository;

import com.oneenterprise.dummyproject.platform.branding.entity.Platform_Entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformBrandingRepository
        extends JpaRepository<Platform_Entity, Long> {

}