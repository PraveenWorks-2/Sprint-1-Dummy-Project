package com.oneenterprise.roleservice.service;

import java.util.List;

import com.oneenterprise.roleservice.dto.RoleRequestDto;
import com.oneenterprise.roleservice.dto.RoleResponseDto;
import com.oneenterprise.roleservice.dto.RoleUpdateDto;

public interface RoleService {
    RoleResponseDto createRole(RoleRequestDto requestDto);
    RoleResponseDto getRoleById(Long id);
    List<RoleResponseDto> getAllRoles(String tenantId, Boolean activeOnly);
    List<RoleResponseDto> getCustomRoles(String tenantId);
    RoleResponseDto updateRole(Long id, RoleUpdateDto updateDto);
    void deactivateRole(Long id);
    void deleteRole(Long id);
}