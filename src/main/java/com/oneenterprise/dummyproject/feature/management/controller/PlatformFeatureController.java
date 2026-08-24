package com.oneenterprise.dummyproject.feature.management.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.oneenterprise.dummyproject.feature.management.dto.PlatformFeatureRequest;
import com.oneenterprise.dummyproject.feature.management.dto.PlatformFeatureResponse;
import com.oneenterprise.dummyproject.feature.management.service.PlatformFeatureService;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class PlatformFeatureController {

    private final PlatformFeatureService service;

    public PlatformFeatureController(
            PlatformFeatureService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformFeatureResponse create(
            @Valid @RequestBody
            PlatformFeatureRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<PlatformFeatureResponse> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public PlatformFeatureResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PlatformFeatureResponse update(
            @PathVariable Long id,
            @Valid @RequestBody
            PlatformFeatureRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }
}
