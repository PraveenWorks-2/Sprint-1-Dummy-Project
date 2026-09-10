package com.example.permission_management_service.dto;

import com.example.permission_management_service.entity.DepartmentPermission;

public record DepartmentPermissionResponse(

        Long id,
        Long departmentId,
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean allowed

) {

    public Long id() {
		return id;
	}

	public Long departmentId() {
		return departmentId;
	}

	public Long permissionId() {
		return permissionId;
	}

	public String permissionCode() {
		return permissionCode;
	}

	public String permissionName() {
		return permissionName;
	}

	public Boolean allowed() {
		return allowed;
	}

	public static DepartmentPermissionResponse from(
            DepartmentPermission dp) {

        return new DepartmentPermissionResponse(

                dp.getId(),
                dp.getDepartmentId(),
                dp.getPermission().getId(),
                dp.getPermission().getCode(),
                dp.getPermission().getName(),
                dp.getAllowed()
        );
    }
}