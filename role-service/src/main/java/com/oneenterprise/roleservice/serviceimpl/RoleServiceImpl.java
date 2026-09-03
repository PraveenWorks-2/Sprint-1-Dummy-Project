package com.oneenterprise.roleservice.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.oneenterprise.roleservice.dto.RoleRequestDto;
import com.oneenterprise.roleservice.dto.RoleResponseDto;
import com.oneenterprise.roleservice.dto.RoleUpdateDto;
import com.oneenterprise.roleservice.entity.Role;
import com.oneenterprise.roleservice.exception.RoleAlreadyExistsException;
import com.oneenterprise.roleservice.exception.RoleNotFoundException;
import com.oneenterprise.roleservice.exception.TenantNotFoundException;
import com.oneenterprise.roleservice.producer.RoleEventProducer;
import com.oneenterprise.roleservice.repository.RoleRepository;
import com.oneenterprise.roleservice.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleEventProducer roleEventProducer;
    private final RestClient restClient;

    public RoleServiceImpl(RoleRepository roleRepository,
                           RoleEventProducer roleEventProducer,
                           @Value("${tenant-service.url:http://localhost:8083}") String tenantServiceUrl) {
        this.roleRepository = roleRepository;
        this.roleEventProducer = roleEventProducer;
        this.restClient = RestClient.builder().baseUrl(tenantServiceUrl).build();
    }

    @Override
    @Transactional
    @CacheEvict(value = "roles_tenant", key = "#requestDto.tenantId")
    public RoleResponseDto createRole(RoleRequestDto requestDto) {
        validateTenant(requestDto.getTenantId());

        if (roleRepository.existsByRoleNameAndTenantId(requestDto.getRoleName(), requestDto.getTenantId())) {
            throw new RoleAlreadyExistsException("Role '" + requestDto.getRoleName() + "' already exists for tenant: " + requestDto.getTenantId());
        }

        Role role = new Role();
        role.setRoleName(requestDto.getRoleName().trim().toUpperCase());
        role.setDescription(requestDto.getDescription());
        role.setTenantId(requestDto.getTenantId());
        role.setIsCustom(requestDto.getIsCustom() != null ? requestDto.getIsCustom() : false);
        role.setIsActive(true);

        Role savedRole = roleRepository.save(role);

        // Kafka Event
        roleEventProducer.publishEvent("ROLE_CREATED", savedRole.getId(), savedRole.getRoleName(), savedRole.getTenantId());

        return mapToDto(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "roles", key = "#id")
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        return mapToDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "roles_tenant", key = "#tenantId")
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
    @CacheEvict(value = {"roles", "roles_tenant"}, allEntries = true)
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

        Role savedRole = roleRepository.save(role);

        // Kafka Event
        roleEventProducer.publishEvent("ROLE_UPDATED", savedRole.getId(), savedRole.getRoleName(), savedRole.getTenantId());

        return mapToDto(savedRole);
    }

    @Override
    @Transactional
    @CacheEvict(value = {"roles", "roles_tenant"}, allEntries = true)
    public void deactivateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        role.setIsActive(false);
        roleRepository.save(role);

        roleEventProducer.publishEvent("ROLE_DEACTIVATED", role.getId(), role.getRoleName(), role.getTenantId());
    }

    @Override
    @Transactional
    @CacheEvict(value = {"roles", "roles_tenant"}, allEntries = true)
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        roleRepository.delete(role);

        roleEventProducer.publishEvent("ROLE_DELETED", role.getId(), role.getRoleName(), role.getTenantId());
    }

    private void validateTenant(String tenantId) {
        // Validate UUID format first so invalid formats trigger 404
        try {
            java.util.UUID.fromString(tenantId);
        } catch (IllegalArgumentException ex) {
            throw new TenantNotFoundException("Tenant not found with ID: " + tenantId);
        }

        try {
            restClient.get()
                    .uri("/api/tenants/{id}", tenantId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                        throw new TenantNotFoundException("Tenant not found with ID: " + tenantId);
                    })
                    .toBodilessEntity();
        } catch (TenantNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TenantNotFoundException("Invalid tenant or Tenant Service unavailable: " + ex.getMessage());
        }
    }

    private RoleResponseDto mapToDto(Role role) {
        return new RoleResponseDto(
                role.getId(),
                role.getRoleName(),
                role.getDescription(),
                role.getTenantId(),
                role.getIsCustom(),
                role.getIsActive(),
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}