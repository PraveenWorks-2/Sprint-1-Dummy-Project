package com.example.permission_management_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.permission_management_service.dto.DepartmentPermissionRequest;
import com.example.permission_management_service.dto.DepartmentPermissionResponse;
import com.example.permission_management_service.entity.DepartmentPermission;
import com.example.permission_management_service.entity.Permission_Entity;
import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;
import com.example.permission_management_service.repository.DepartmentPermissionRepository;
import com.example.permission_management_service.repository.PermissionRepository;

@Service
@Transactional
public class DepartmentPermissionServiceImpl
        implements DepartmentPermissionService {

    private final DepartmentPermissionRepository repository;
    private final PermissionRepository permissionRepository;

    public DepartmentPermissionServiceImpl(
            DepartmentPermissionRepository repository,
            PermissionRepository permissionRepository) {

        this.repository = repository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public DepartmentPermissionResponse assign(
            DepartmentPermissionRequest request) {

        if (repository.existsByDepartmentIdAndPermissionId(
                request.getDepartmentId(),
                request.getPermissionId())) {

            throw new DuplicateResourceException(
                    "Permission already assigned to department");
        }

        Permission_Entity permission =
                permissionRepository.findById(
                        request.getPermissionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Permission not found with id: "
                                + request.getPermissionId()));

        DepartmentPermission entity =
                new DepartmentPermission();

        entity.setDepartmentId(request.getDepartmentId());
        entity.setPermission(permission);
        entity.setAllowed(
                request.getAllowed() == null
                        || request.getAllowed());

        return DepartmentPermissionResponse.from(
                repository.save(entity));
    }

    @Override
    public List<DepartmentPermissionResponse>
    getByDepartment(Long departmentId) {

        return repository.findByDepartmentId(departmentId)
                .stream()
                .map(DepartmentPermissionResponse::from)
                .toList();
    }

    @Override
    public List<DepartmentPermissionResponse>
    getActiveByDepartment(Long departmentId) {

        return repository
                .findByDepartmentIdAndAllowedTrue(departmentId)
                .stream()
                .map(DepartmentPermissionResponse::from)
                .toList();
    }

    @Override
    public boolean hasPermission(
            Long departmentId,
            String permissionCode) {

        return repository
                .findByDepartmentIdAndAllowedTrue(departmentId)
                .stream()
                .anyMatch(dp ->
                        dp.getPermission()
                                .getCode()
                                .equalsIgnoreCase(permissionCode));
    }

    @Override
    public void remove(
            Long departmentId,
            Long permissionId) {

        if (!repository.existsByDepartmentIdAndPermissionId(
                departmentId,
                permissionId)) {

            throw new ResourceNotFoundException(
                    "Department permission not found");
        }

        repository.deleteByDepartmentIdAndPermissionId(
                departmentId,
                permissionId);
    }
}