package com.oneenterprise.tenant.service;
import com.oneenterprise.tenant.dto.ProvisioningResponse;
import java.util.UUID;
public interface ProvisioningService { ProvisioningResponse provision(UUID tenantId); ProvisioningResponse get(UUID tenantId); }
