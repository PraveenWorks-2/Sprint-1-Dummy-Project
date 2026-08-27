package com.oneenterprise.dummyproject.platform.branding.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingRequest;
import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingResponse;
import com.oneenterprise.dummyproject.platform.branding.service.PlatformBrandingService;

import java.util.List;

@RestController
@RequestMapping("/api/branding")
public class PlatformBrandingController {

    private final PlatformBrandingService service;

    public PlatformBrandingController(
            PlatformBrandingService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlatformBrandingResponse create(
            @Valid @RequestBody
            PlatformBrandingRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<PlatformBrandingResponse> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public PlatformBrandingResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PlatformBrandingResponse update(
            @PathVariable Long id,
            @Valid @RequestBody
            PlatformBrandingRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }
}
