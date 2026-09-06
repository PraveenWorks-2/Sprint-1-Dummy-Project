package com.oneenterprise.roleservice.dto;

public class RoleUpdateDto {

    private String roleName;
    private String description;
    private Boolean isActive;

    public RoleUpdateDto() {
    }

    public RoleUpdateDto(String roleName, String description, Boolean isActive) {
        this.roleName = roleName;
        this.description = description;
        this.isActive = isActive;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}