package com.oneenterprise.dummyproject.role_permission.service;

import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionRequestDto;
import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionResponseDto;

import java.util.List;

public interface RolePermissionService {

    RolePermissionResponseDto assignPermissionToRole(RolePermissionRequestDto requestDto);

    void removePermissionFromRole(Long roleId, Long permissionId);

    List<RolePermissionResponseDto> getPermissionsByRole(Long roleId);

    List<RolePermissionResponseDto> getPermissionMatrix();
}
