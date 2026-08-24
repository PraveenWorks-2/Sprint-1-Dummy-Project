package com.oneenterprise.dummyproject.platform.license.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.oneenterprise.dummyproject.platform.license.dto.LicenseRequest;
import com.oneenterprise.dummyproject.platform.license.dto.LicenseResponse;
import com.oneenterprise.dummyproject.platform.license.service.LicenseService;

import jakarta.validation.Valid;

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