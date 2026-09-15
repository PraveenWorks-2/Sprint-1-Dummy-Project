package com.example.permission_management_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PermissionMatrixRequest {

	 @NotBlank(message = "Role name is required")
	    private String roleName;

	    @NotNull(message = "Permission ID is required")
	    private Long permissionId;

	    public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
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

		public PermissionMatrixRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public PermissionMatrixRequest(@NotBlank(message = "Role name is required") String roleName,
				@NotNull(message = "Permission ID is required") Long permissionId, Boolean allowed) {
			super();
			this.roleName = roleName;
			this.permissionId = permissionId;
			this.allowed = allowed;
		}

		private Boolean allowed = true;
}
