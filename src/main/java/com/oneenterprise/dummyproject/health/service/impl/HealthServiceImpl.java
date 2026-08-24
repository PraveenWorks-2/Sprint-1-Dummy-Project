package com.oneenterprise.dummyproject.health.service.impl;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.health.dto.HealthResponse;
import com.oneenterprise.dummyproject.health.service.HealthService;

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