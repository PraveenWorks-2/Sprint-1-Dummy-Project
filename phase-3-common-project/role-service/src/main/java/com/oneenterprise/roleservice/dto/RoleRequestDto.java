package com.oneenterprise.roleservice.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleRequestDto {

    @NotBlank(message = "Role name is required")
    private String roleName;

    private String description;

    @NotBlank(message = "Tenant ID is required")
    private String tenantId;

    private Boolean isCustom;

    public RoleRequestDto() {
    }

    public RoleRequestDto(String roleName, String description, String tenantId, Boolean isCustom) {
        this.roleName = roleName;
        this.description = description;
        this.tenantId = tenantId;
        this.isCustom = isCustom;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(Boolean isCustom) {
        this.isCustom = isCustom;
    }
}