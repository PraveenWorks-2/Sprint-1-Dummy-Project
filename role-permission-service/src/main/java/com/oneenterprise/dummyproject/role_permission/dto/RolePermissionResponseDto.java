package com.oneenterprise.dummyproject.role_permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RolePermissionResponseDto {

    private Long id;

    private Long roleId;

    private Long permissionId;

    private LocalDateTime createdAt;
}
