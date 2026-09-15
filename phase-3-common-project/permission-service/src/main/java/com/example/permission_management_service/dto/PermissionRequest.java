package com.example.permission_management_service.dto;

import com.example.permission_management_service.entity.PermissionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PermissionRequest {

    @NotBlank(message = "Permission name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Permission code is required")
    @Size(max = 100)
    private String code;

    @Size(max = 500)
    private String description;

    @NotBlank(message = "Permission category is required")
    @Size(max = 50)
    private String category;

    @NotBlank(message = "Module is required")
    @Size(max = 100)
    private String module;

    @NotNull(message = "Permission type is required")
    private PermissionType permissionType;

    public PermissionRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModule() {
        return module;
    }

    public PermissionRequest(@NotBlank(message = "Permission name is required") @Size(max = 100) String name,
			@NotBlank(message = "Permission code is required") @Size(max = 100) String code,
			@Size(max = 500) String description,
			@NotBlank(message = "Permission category is required") @Size(max = 50) String category,
			@NotBlank(message = "Module is required") @Size(max = 100) String module,
			@NotNull(message = "Permission type is required") PermissionType permissionType) {
		super();
		this.name = name;
		this.code = code;
		this.description = description;
		this.category = category;
		this.module = module;
		this.permissionType = permissionType;
	}

	public void setModule(String module) {
        this.module = module;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}