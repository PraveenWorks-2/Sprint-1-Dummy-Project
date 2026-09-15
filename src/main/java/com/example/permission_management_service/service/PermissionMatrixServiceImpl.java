package com.example.permission_management_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.permission_management_service.dto.PermissionMatrixRequest;
import com.example.permission_management_service.dto.PermissionMatrixResponse;

import com.example.permission_management_service.entity.PermissionMatrix;
import com.example.permission_management_service.entity.Permission_Entity;

import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;

import com.example.permission_management_service.kafka.PermissionEventPublisher;

import com.example.permission_management_service.repository.PermissionMatrixRepository;
import com.example.permission_management_service.repository.PermissionRepository;

@Service
@Transactional
public class PermissionMatrixServiceImpl
        implements PermissionMatrixService {

    private final PermissionMatrixRepository repository;

    private final PermissionRepository permissionRepository;

    private final PermissionEventPublisher eventPublisher;

    public PermissionMatrixServiceImpl(
            PermissionMatrixRepository repository,
            PermissionRepository permissionRepository,
            PermissionEventPublisher eventPublisher) {

        this.repository = repository;

        this.permissionRepository =
                permissionRepository;

        this.eventPublisher =
                eventPublisher;
    }

    @Override
    public PermissionMatrixResponse assign(
            PermissionMatrixRequest request) {

        String role =
                request.getRoleName()
                        .trim()
                        .toUpperCase();

        if (repository
                .existsByRoleNameIgnoreCaseAndPermissionId(
                        role,
                        request.getPermissionId())) {

            throw new DuplicateResourceException(
                    "Permission already assigned to role"
            );
        }

        Permission_Entity permission =
                permissionRepository.findById(
                        request.getPermissionId()
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Permission not found with id: "
                                        + request.getPermissionId()
                        )
                );

        PermissionMatrix matrix =
                new PermissionMatrix();

        matrix.setRoleName(role);

        matrix.setPermission(
                permission
        );

        matrix.setAllowed(
                request.getAllowed() == null
                        || request.getAllowed()
        );

        PermissionMatrix saved =
                repository.save(matrix);

        PermissionMatrixResponse response =
                PermissionMatrixResponse.from(
                        saved
                );

        eventPublisher.publish(
                "PERMISSION_MATRIX_ASSIGNED",
                "PERMISSION_MATRIX",
                String.valueOf(saved.getId()),
                Map.of(
                        "id", saved.getId(),
                        "roleName", saved.getRoleName(),
                        "permissionId",
                        saved.getPermission().getId(),
                        "allowed",
                        saved.getAllowed()
                )
        );

        return response;
    }

    @Override
    public List<PermissionMatrixResponse>
    getByRole(String roleName) {

        return repository
                .findByRoleNameIgnoreCase(roleName)
                .stream()
                .map(PermissionMatrixResponse::from)
                .toList();
    }

    @Override
    public List<PermissionMatrixResponse>
    getActiveByRole(String roleName) {

        return repository
                .findByRoleNameIgnoreCaseAndAllowedTrue(
                        roleName
                )
                .stream()
                .map(PermissionMatrixResponse::from)
                .toList();
    }

    @Override
    public boolean hasPermission(
            String roleName,
            String permissionCode) {

        return repository
                .findByRoleNameIgnoreCaseAndAllowedTrue(
                        roleName
                )
                .stream()
                .anyMatch(
                        matrix ->
                                matrix.getPermission()
                                        .getCode()
                                        .equalsIgnoreCase(
                                                permissionCode
                                        )
                );
    }

    @Override
    public void remove(
            String roleName,
            Long permissionId) {

        PermissionMatrix matrix =
                repository
                        .findByRoleNameIgnoreCaseAndPermissionId(
                                roleName,
                                permissionId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Permission matrix entry not found"
                                )
                        );

        Long matrixId =
                matrix.getId();

        String role =
                matrix.getRoleName();

        repository.delete(matrix);

        eventPublisher.publish(
                "PERMISSION_MATRIX_REMOVED",
                "PERMISSION_MATRIX",
                String.valueOf(matrixId),
                Map.of(
                        "id", matrixId,
                        "roleName", role,
                        "permissionId", permissionId
                )
        );
    }
}