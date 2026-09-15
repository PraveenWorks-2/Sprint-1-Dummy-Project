package com.oneenterprise.dummyproject.platform.health.service.impl;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.platform.health.dto.HealthResponse;
import com.oneenterprise.dummyproject.platform.health.service.HealthService;

@Service
public class HealthServiceImpl
        implements HealthService {

    @Override
    public HealthResponse getHealth() {

        return new HealthResponse(
                "Platform Application",
                "UP",
                "UP"
        );
    }
}