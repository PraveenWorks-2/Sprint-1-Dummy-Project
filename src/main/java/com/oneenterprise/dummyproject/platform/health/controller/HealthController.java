package com.oneenterprise.dummyproject.platform.health.controller;

import org.springframework.web.bind.annotation.*;

import com.oneenterprise.dummyproject.platform.health.dto.HealthResponse;
import com.oneenterprise.dummyproject.platform.health.service.HealthService;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService service;

    public HealthController(
            HealthService service) {

        this.service = service;
    }

    @GetMapping
    public HealthResponse getHealth() {

        return service.getHealth();
    }
}
