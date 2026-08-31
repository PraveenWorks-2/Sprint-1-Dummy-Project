package com.oneenterprise.tenant.controller;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationResponse;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.dto.TenantStatusRequest;
import com.oneenterprise.tenant.dto.UpdateTenantRequest;
import com.oneenterprise.tenant.service.TenantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public ResponseEntity<TenantResponse> createTenant(
            @Valid @RequestBody CreateTenantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tenantService.createTenant(request));
    }

    @GetMapping
    public ResponseEntity<List<TenantResponse>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantResponse> updateTenant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTenantRequest request) {
        return ResponseEntity.ok(tenantService.updateTenant(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateTenant(@PathVariable UUID id) {
        tenantService.deactivateTenant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<TenantResponse> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getProfile(id));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<TenantResponse> updateProfile(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTenantRequest request) {
        return ResponseEntity.ok(tenantService.updateProfile(id, request));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<TenantResponse> getStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getStatus(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TenantResponse> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody TenantStatusRequest request) {
        return ResponseEntity.ok(tenantService.updateStatus(id, request));
    }

    @GetMapping("/{id}/configuration")
    public ResponseEntity<TenantConfigurationResponse> getConfiguration(
            @PathVariable UUID id) {
        return ResponseEntity.ok(tenantService.getConfiguration(id));
    }

    @PutMapping("/{id}/configuration")
    public ResponseEntity<TenantConfigurationResponse> updateConfiguration(
            @PathVariable UUID id,
            @Valid @RequestBody TenantConfigurationRequest request) {
        return ResponseEntity.ok(
                tenantService.updateConfiguration(id, request));
    }
}
