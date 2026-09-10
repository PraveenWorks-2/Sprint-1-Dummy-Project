package com.oneenterprise.dummyproject.role_permission.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RolePermissionRequestDto {

    @NotNull(message = "Role ID is required")
    @Positive(message = "Role ID must be greater than 0")
    private Long roleId;

    @NotNull(message = "Permission ID is required")
    @Positive(message = "Permission ID must be greater than 0")
    private Long permissionId;
}
