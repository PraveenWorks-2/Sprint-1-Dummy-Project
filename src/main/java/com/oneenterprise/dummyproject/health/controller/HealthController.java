package com.oneenterprise.dummyproject.health.controller;

import org.springframework.web.bind.annotation.*;

import com.oneenterprise.dummyproject.health.dto.HealthResponse;
import com.oneenterprise.dummyproject.health.service.HealthService;

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
