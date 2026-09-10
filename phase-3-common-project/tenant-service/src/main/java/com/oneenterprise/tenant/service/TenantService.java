package com.oneenterprise.tenant.service;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationResponse;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.dto.TenantStatusRequest;
import com.oneenterprise.tenant.dto.UpdateTenantRequest;

import java.util.List;
import java.util.UUID;

public interface TenantService {

    TenantResponse createTenant(CreateTenantRequest request);
    List<TenantResponse> getAllTenants();
    TenantResponse getTenantById(UUID id);
    TenantResponse updateTenant(UUID id, UpdateTenantRequest request);
    TenantResponse updateStatus(UUID id, TenantStatusRequest request);
    TenantResponse getProfile(UUID id);
    TenantResponse updateProfile(UUID id, UpdateTenantRequest request);
    TenantResponse getStatus(UUID id);
    TenantConfigurationResponse getConfiguration(UUID id);
    TenantConfigurationResponse updateConfiguration(UUID id, TenantConfigurationRequest request);
    void deactivateTenant(UUID id);
}
