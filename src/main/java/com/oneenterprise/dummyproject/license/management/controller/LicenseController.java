package com.oneenterprise.dummyproject.license.management.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.oneenterprise.dummyproject.license.management.dto.LicenseRequest;
import com.oneenterprise.dummyproject.license.management.dto.LicenseResponse;
import com.oneenterprise.dummyproject.license.management.service.LicenseService;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseController {

    private final LicenseService service;
    
	public LicenseController(
            LicenseService service) {

        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LicenseResponse create(
            @Valid @RequestBody
            LicenseRequest request) {

        return service.create(request);
    }

    @GetMapping
    public List<LicenseResponse> getAll() {

        return service.getAll();
    }

    @GetMapping("/{id}")
    public LicenseResponse getById(
            @PathVariable Long id) {

        return service.getById(id);
    }

    @PutMapping("/{id}")
    public LicenseResponse update(
            @PathVariable Long id,
            @Valid @RequestBody
            LicenseRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }
}