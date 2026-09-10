package com.example.permission_management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DataPermissionRequest {
	
	@NotNull(message = "Permission ID is required")
    private Long permissionId;

    @NotBlank(message = "Data scope is required")
    @Size(max = 50)
    private String dataScope;

    public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getFilterExpression() {
		return filterExpression;
	}

	public void setFilterExpression(String filterExpression) {
		this.filterExpression = filterExpression;
	}

	public DataPermissionRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DataPermissionRequest(@NotNull(message = "Permission ID is required") Long permissionId,
			@NotBlank(message = "Data scope is required") @Size(max = 50) String dataScope,
			@Size(max = 500) String filterExpression) {
		super();
		this.permissionId = permissionId;
		this.dataScope = dataScope;
		this.filterExpression = filterExpression;
	}

	@Size(max = 500)
    private String filterExpression;

}
