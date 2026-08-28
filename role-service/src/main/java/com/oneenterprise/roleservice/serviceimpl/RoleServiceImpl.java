package com.oneenterprise.roleservice.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oneenterprise.roleservice.dto.RoleRequestDto;
import com.oneenterprise.roleservice.dto.RoleResponseDto;
import com.oneenterprise.roleservice.dto.RoleUpdateDto;
import com.oneenterprise.roleservice.entity.Role;
import com.oneenterprise.roleservice.exception.RoleAlreadyExistsException;
import com.oneenterprise.roleservice.exception.RoleNotFoundException;
import com.oneenterprise.roleservice.repository.RoleRepository;
import com.oneenterprise.roleservice.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public RoleResponseDto createRole(RoleRequestDto requestDto) {
        if (roleRepository.existsByRoleNameAndTenantId(requestDto.getRoleName(), requestDto.getTenantId())) {
            throw new RoleAlreadyExistsException("Role '" + requestDto.getRoleName() + "' already exists for tenant: " + requestDto.getTenantId());
        }

        Role role = Role.builder()
                .roleName(requestDto.getRoleName().trim().toUpperCase())
                .description(requestDto.getDescription())
                .tenantId(requestDto.getTenantId())
                .isCustom(requestDto.getIsCustom() != null ? requestDto.getIsCustom() : false)
                .isActive(true)
                .build();

        return mapToDto(roleRepository.save(role));
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        return mapToDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDto> getAllRoles(String tenantId, Boolean activeOnly) {
        List<Role> roles = (activeOnly != null && activeOnly)
                ? roleRepository.findByTenantIdAndIsActiveTrue(tenantId)
                : roleRepository.findByTenantId(tenantId);

        return roles.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDto> getCustomRoles(String tenantId) {
        return roleRepository.findByTenantIdAndIsCustom(tenantId, true)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleResponseDto updateRole(Long id, RoleUpdateDto updateDto) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));

        if (updateDto.getRoleName() != null && !updateDto.getRoleName().isBlank()) {
            String updatedName = updateDto.getRoleName().trim().toUpperCase();
            if (!role.getRoleName().equalsIgnoreCase(updatedName) &&
                roleRepository.existsByRoleNameAndTenantId(updatedName, role.getTenantId())) {
                throw new RoleAlreadyExistsException("Role '" + updatedName + "' already exists for tenant: " + role.getTenantId());
            }
            role.setRoleName(updatedName);
        }

        if (updateDto.getDescription() != null) {
            role.setDescription(updateDto.getDescription());
        }

        if (updateDto.getIsActive() != null) {
            role.setIsActive(updateDto.getIsActive());
        }

        return mapToDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void deactivateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        role.setIsActive(false);
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role not found with ID: " + id);
        }
        roleRepository.deleteById(id);
    }

    private RoleResponseDto mapToDto(Role role) {
        return RoleResponseDto.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .tenantId(role.getTenantId())
                .isCustom(role.getIsCustom())
                .isActive(role.getIsActive())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }
}