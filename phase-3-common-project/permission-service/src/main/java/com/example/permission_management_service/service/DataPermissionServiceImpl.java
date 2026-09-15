package com.example.permission_management_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.permission_management_service.dto.DataPermissionRequest;
import com.example.permission_management_service.dto.DataPermissionResponse;

import com.example.permission_management_service.entity.DataPermission;
import com.example.permission_management_service.entity.Permission_Entity;

import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;

import com.example.permission_management_service.kafka.PermissionEventPublisher;

import com.example.permission_management_service.repository.DataPermissionRepository;
import com.example.permission_management_service.repository.PermissionRepository;

@Service
@Transactional
public class DataPermissionServiceImpl
        implements DataPermissionService {

    private final DataPermissionRepository repository;

    private final PermissionRepository permissionRepository;

    private final PermissionEventPublisher eventPublisher;

    public DataPermissionServiceImpl(
            DataPermissionRepository repository,
            PermissionRepository permissionRepository,
            PermissionEventPublisher eventPublisher) {

        this.repository = repository;

        this.permissionRepository =
                permissionRepository;

        this.eventPublisher =
                eventPublisher;
    }

    @Override
    public DataPermissionResponse create(
            DataPermissionRequest request) {

        String scope =
                request.getDataScope()
                        .trim()
                        .toUpperCase();

        if (repository
                .findByPermissionIdAndDataScope(
                        request.getPermissionId(),
                        scope
                )
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Data permission already exists"
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

        DataPermission entity =
                new DataPermission();

        entity.setPermission(
                permission
        );

        entity.setDataScope(
                scope
        );

        entity.setFilterExpression(
                request.getFilterExpression()
        );

        entity.setActive(true);

        DataPermission saved =
                repository.save(entity);

        DataPermissionResponse response =
                DataPermissionResponse.from(
                        saved
                );

        eventPublisher.publish(
                "DATA_PERMISSION_CREATED",
                "DATA_PERMISSION",
                String.valueOf(saved.getId()),
                Map.of(
                        "id", saved.getId(),
                        "permissionId",
                        saved.getPermission().getId(),
                        "dataScope",
                        saved.getDataScope()
                )
        );

        return response;
    }

    @Override
    public List<DataPermissionResponse>
    getByPermission(Long permissionId) {

        return repository
                .findByPermissionId(permissionId)
                .stream()
                .map(DataPermissionResponse::from)
                .toList();
    }

    @Override
    public List<DataPermissionResponse>
    getByScope(String dataScope) {

        return repository
                .findByDataScopeIgnoreCase(dataScope)
                .stream()
                .map(DataPermissionResponse::from)
                .toList();
    }

    @Override
    public List<DataPermissionResponse>
    getActive() {

        return repository
                .findByActiveTrue()
                .stream()
                .map(DataPermissionResponse::from)
                .toList();
    }
}