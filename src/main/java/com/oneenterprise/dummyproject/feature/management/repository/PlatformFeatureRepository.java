package com.oneenterprise.dummyproject.feature.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.dummyproject.feature.management.entity.PlatformFeature;

public interface PlatformFeatureRepository
        extends JpaRepository<PlatformFeature, Long> {

}