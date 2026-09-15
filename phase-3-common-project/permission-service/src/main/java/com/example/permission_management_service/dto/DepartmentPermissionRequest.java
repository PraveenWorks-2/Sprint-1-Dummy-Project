package com.example.permission_management_service.dto;

import jakarta.validation.constraints.NotNull;

public class DepartmentPermissionRequest {

    @NotNull(message = "Department ID is required")
    private Long departmentId;

    @NotNull(message = "Permission ID is required")
    private Long permissionId;

    private Boolean allowed = true;

    public Long getDepartmentId() {
        return departmentId;
    }

    public DepartmentPermissionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentPermissionRequest(@NotNull(message = "Department ID is required") Long departmentId,
			@NotNull(message = "Permission ID is required") Long permissionId, Boolean allowed) {
		super();
		this.departmentId = departmentId;
		this.permissionId = permissionId;
		this.allowed = allowed;
	}

	public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
        this.allowed = allowed;
    }
}