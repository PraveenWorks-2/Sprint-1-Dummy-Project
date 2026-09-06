package com.oneenterprise.dummyproject.role_permission.service.impl;

import com.oneenterprise.dummyproject.role_permission.client.PermissionClient;
import com.oneenterprise.dummyproject.role_permission.client.RoleClient;
import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionRequestDto;
import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionResponseDto;
import com.oneenterprise.dummyproject.role_permission.entity.RolePermission;
import com.oneenterprise.dummyproject.role_permission.exception.DuplicateResourceException;
import com.oneenterprise.dummyproject.role_permission.exception.ResourceNotFoundException;
import com.oneenterprise.dummyproject.role_permission.repository.RolePermissionRepository;
import com.oneenterprise.dummyproject.role_permission.service.RolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RolePermissionServiceImpl implements RolePermissionService {

    public final RolePermissionRepository rolePermissionRepository;

    private final RoleClient roleClient;

    private final PermissionClient permissionClient;

    @Override
    public RolePermissionResponseDto assignPermissionToRole(RolePermissionRequestDto requestDto) {

        roleClient.getRoleById(requestDto.getRoleId());

        permissionClient.getPermissionById(requestDto.getPermissionId());

        if (rolePermissionRepository.existsByRoleIdAndPermissionId(
                requestDto.getRoleId(),
                requestDto.getPermissionId())) {

            throw new DuplicateResourceException("Permission is already assigned to this role");
        }

        RolePermission rolePermission = new RolePermission();

        rolePermission.setRoleId(requestDto.getRoleId());
        rolePermission.setPermissionId(requestDto.getPermissionId());
        rolePermission.setCreatedAt(LocalDateTime.now());

        RolePermission savedRolePermission =
                rolePermissionRepository.save(rolePermission);

        return convertToResponse(savedRolePermission);
    }

    @Override
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        RolePermission rolePermission = rolePermissionRepository
                .findByRoleIdAndPermissionId(roleId, permissionId)
                .orElseThrow(() -> new ResourceNotFoundException
                        ("Role-Permission mapping not found"));

        rolePermissionRepository.delete(rolePermission);
    }

    @Override
    public List<RolePermissionResponseDto> getPermissionsByRole(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepository.findByRoleId(roleId);

        return rolePermissions.stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<RolePermissionResponseDto> getPermissionMatrix() {
        List<RolePermission> rolePermissions =rolePermissionRepository.findAll();

        return rolePermissions.stream().map(this::convertToResponse).toList();
    }

    private RolePermissionResponseDto convertToResponse(RolePermission rolePermission) {

        RolePermissionResponseDto responseDto = new RolePermissionResponseDto();

        responseDto.setId(rolePermission.getId());
        responseDto.setRoleId(rolePermission.getRoleId());
        responseDto.setPermissionId(rolePermission.getPermissionId());
        responseDto.setCreatedAt(rolePermission.getCreatedAt());

        return responseDto;
    }
}

