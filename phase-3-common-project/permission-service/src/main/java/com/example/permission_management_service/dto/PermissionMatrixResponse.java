package com.example.permission_management_service.dto;

import com.example.permission_management_service.entity.PermissionMatrix;

public record PermissionMatrixResponse(

        Long id,
        String roleName,
        Long permissionId,
        String permissionCode,
        String permissionName,
        Boolean allowed

) {

    public static PermissionMatrixResponse from(
            PermissionMatrix matrix) {

        return new PermissionMatrixResponse(

                matrix.getId(),
                matrix.getRoleName(),
                matrix.getPermission().getId(),
                matrix.getPermission().getCode(),
                matrix.getPermission().getName(),
                matrix.getAllowed()
        );
    }

	public Long id() {
		return id;
	}

	public String roleName() {
		return roleName;
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
}