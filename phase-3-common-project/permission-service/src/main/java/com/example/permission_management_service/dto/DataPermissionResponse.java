package com.example.permission_management_service.dto;

import com.example.permission_management_service.entity.DataPermission;

public record DataPermissionResponse(

        Long id,
        Long permissionId,
        String permissionCode,
        String dataScope,
        String filterExpression,
        Boolean active

) {

    public Long id() {
		return id;
	}

	public Long permissionId() {
		return permissionId;
	}

	public String permissionCode() {
		return permissionCode;
	}

	public String dataScope() {
		return dataScope;
	}

	public String filterExpression() {
		return filterExpression;
	}

	public Boolean active() {
		return active;
	}

	public static DataPermissionResponse from(
            DataPermission dataPermission) {

        return new DataPermissionResponse(

                dataPermission.getId(),

                dataPermission.getPermission().getId(),

                dataPermission.getPermission().getCode(),

                dataPermission.getDataScope(),

                dataPermission.getFilterExpression(),

                dataPermission.getActive()
        );
    }
}