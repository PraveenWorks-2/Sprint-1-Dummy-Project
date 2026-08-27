package com.oneenterprise.dummyproject.platform.license.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.dummyproject.platform.license.entity.License;

public interface LicenseRepository
        extends JpaRepository<License, Long> {

}