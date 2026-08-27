package com.oneenterprise.dummyproject.role.service;

import com.oneenterprise.dummyproject.role.dto.RoleRequestDto;
import com.oneenterprise.dummyproject.role.dto.RoleResponseDto;
import com.oneenterprise.dummyproject.role.dto.RoleUpdateDto;

import java.util.List;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto requestDto);
    RoleResponseDto getRoleById(Long id);
    List<RoleResponseDto> getAllRoles(String tenantId, Boolean activeOnly);
    List<RoleResponseDto> getCustomRoles(String tenantId);
    RoleResponseDto updateRole(Long id, RoleUpdateDto updateDto);
    void deactivateRole(Long id);
    void deleteRole(Long id);
}