package com.oneenterprise.tenant.controller;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationResponse;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.dto.TenantStatusRequest;
import com.oneenterprise.tenant.dto.UpdateTenantRequest;
import com.oneenterprise.tenant.service.TenantService;
import com.oneenterprise.tenant.service.ProvisioningService;
import com.oneenterprise.tenant.service.BackupService;
import com.oneenterprise.tenant.dto.ProvisioningResponse;
import com.oneenterprise.tenant.dto.BackupResponse;
import com.oneenterprise.tenant.dto.IsolationResponse;
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
    private final ProvisioningService provisioningService;
    private final BackupService backupService;

    public TenantController(TenantService tenantService, ProvisioningService provisioningService, BackupService backupService) {
        this.tenantService = tenantService;
        this.provisioningService = provisioningService;
        this.backupService = backupService;
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

    @PostMapping("/{id}/provisioning")
    public ResponseEntity<ProvisioningResponse> provision(@PathVariable UUID id) {
        return ResponseEntity.ok(provisioningService.provision(id));
    }

    @GetMapping("/{id}/provisioning")
    public ResponseEntity<ProvisioningResponse> getProvisioning(@PathVariable UUID id) {
        return ResponseEntity.ok(provisioningService.get(id));
    }

    @GetMapping("/{id}/isolation")
    public ResponseEntity<IsolationResponse> getIsolation(@PathVariable UUID id) {
        ProvisioningResponse p = provisioningService.get(id);
        IsolationResponse response = new IsolationResponse();
        response.setTenantId(id);
        response.setIsolationMode(p.getIsolationMode());
        response.setDatabaseName(p.getDatabaseName());
        response.setSchemaName(p.getSchemaName());
        response.setEnforcement("Tenant-scoped resources are resolved by tenant ID; schema-per-tenant provisioning creates a dedicated PostgreSQL schema for this tenant.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/backups")
    public ResponseEntity<BackupResponse> requestBackup(@PathVariable UUID id) {
        return ResponseEntity.accepted().body(backupService.requestBackup(id));
    }

    @GetMapping("/{id}/backups")
    public ResponseEntity<List<BackupResponse>> backupHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(backupService.history(id));
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
