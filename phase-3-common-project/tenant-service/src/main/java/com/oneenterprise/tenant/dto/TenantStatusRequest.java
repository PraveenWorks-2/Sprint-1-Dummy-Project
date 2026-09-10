package com.oneenterprise.tenant.dto;

import com.oneenterprise.tenant.entity.TenantStatus;
import jakarta.validation.constraints.NotNull;

public class TenantStatusRequest {

    @NotNull
    private TenantStatus status;

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }
}
