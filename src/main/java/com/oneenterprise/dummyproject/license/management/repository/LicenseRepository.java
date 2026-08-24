package com.oneenterprise.dummyproject.license.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.dummyproject.license.management.entity.License;

public interface LicenseRepository
        extends JpaRepository<License, Long> {

}