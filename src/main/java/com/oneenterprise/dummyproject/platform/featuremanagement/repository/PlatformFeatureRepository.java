package com.oneenterprise.dummyproject.platform.featuremanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.dummyproject.platform.featuremanagement.entity.PlatformFeature;

public interface PlatformFeatureRepository
        extends JpaRepository<PlatformFeature, Long> {

}