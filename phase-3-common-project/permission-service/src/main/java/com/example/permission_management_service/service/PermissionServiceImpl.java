package com.example.permission_management_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.permission_management_service.dto.PermissionRequest;
import com.example.permission_management_service.dto.PermissionResponse;
import com.example.permission_management_service.dto.PermissionUpdateRequest;

import com.example.permission_management_service.entity.Permission_Entity;

import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;

import com.example.permission_management_service.kafka.PermissionEventPublisher;

import com.example.permission_management_service.repository.PermissionRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PermissionServiceImpl
        implements PermissionService {

    private final PermissionRepository repository;

    private final PermissionEventPublisher eventPublisher;

    public PermissionServiceImpl(
            PermissionRepository repository,
            PermissionEventPublisher eventPublisher) {

        this.repository = repository;

        this.eventPublisher =
                eventPublisher;
    }

    @Override
    public PermissionResponse create(
            PermissionRequest request) {

        String code =
                normalizeCode(request.getCode());

        if (repository.existsByCode(code)) {

            throw new DuplicateResourceException(
                    "Permission already exists with code: "
                            + code
            );
        }

        Permission_Entity permission =
                new Permission_Entity();

        permission.setName(
                request.getName()
        );

        permission.setCode(
                code
        );

        permission.setCategory(
                request.getCategory()
        );

        permission.setModule(
                request.getModule()
        );

        permission.setDescription(
                request.getDescription()
        );

        permission.setPermissionType(
                request.getPermissionType()
        );

        permission.setActive(true);

        Permission_Entity saved =
                repository.save(permission);

        PermissionResponse response =
                PermissionResponse.from(saved);

        // Kafka event
        eventPublisher.publish(
                "PERMISSION_CREATED",
                "PERMISSION",
                String.valueOf(saved.getId()),
                Map.of(
                        "id", saved.getId(),
                        "code", saved.getCode(),
                        "name", saved.getName(),
                        "category", saved.getCategory(),
                        "module", saved.getModule(),
                        "active", saved.getActive()
                )
        );

        return response;
    }

    @Override
    public List<PermissionResponse> getAll() {

        return repository.findAll()
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    @Override
    public PermissionResponse getById(
            Long id) {

        return PermissionResponse.from(
                findPermission(id)
        );
    }

    @Override
    public PermissionResponse update(
            Long id,
            PermissionUpdateRequest request) {

        Permission_Entity permission =
                findPermission(id);

        permission.setName(
                request.getName().trim()
        );

        permission.setDescription(
                request.getDescription()
        );

        permission.setCategory(
                request.getCategory()
                        .trim()
                        .toUpperCase()
        );

        permission.setModule(
                request.getModule()
                        .trim()
        );

        permission.setPermissionType(
                request.getPermissionType()
        );

        if (request.getActive() != null) {

            permission.setActive(
                    request.getActive()
            );
        }

        Permission_Entity saved =
                repository.save(permission);

        PermissionResponse response =
                PermissionResponse.from(saved);

        eventPublisher.publish(
                "PERMISSION_UPDATED",
                "PERMISSION",
                String.valueOf(saved.getId()),
                Map.of(
                        "id", saved.getId(),
                        "code", saved.getCode(),
                        "name", saved.getName(),
                        "active", saved.getActive()
                )
        );

        return response;
    }

    @Override
    public void delete(Long id) {

        Permission_Entity permission =
                findPermission(id);

        String code =
                permission.getCode();

        repository.delete(permission);

        eventPublisher.publish(
                "PERMISSION_DELETED",
                "PERMISSION",
                String.valueOf(id),
                Map.of(
                        "id", id,
                        "code", code
                )
        );
    }

    @Override
    public PermissionResponse deactivate(
            Long id) {

        Permission_Entity permission =
                findPermission(id);

        permission.setActive(false);

        Permission_Entity saved =
                repository.save(permission);

        PermissionResponse response =
                PermissionResponse.from(saved);

        eventPublisher.publish(
                "PERMISSION_DEACTIVATED",
                "PERMISSION",
                String.valueOf(saved.getId()),
                Map.of(
                        "id", saved.getId(),
                        "code", saved.getCode(),
                        "active", false
                )
        );

        return response;
    }

    @Override
    public List<PermissionResponse>
    getByCategory(String category) {

        return repository
                .findByCategoryIgnoreCase(category)
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    @Override
    public List<PermissionResponse>
    getByModule(String module) {

        return repository
                .findByModuleIgnoreCase(module)
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    private Permission_Entity findPermission(
            Long id) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Permission not found with id: "
                                        + id
                        )
                );
    }

    private String normalizeCode(
            String code) {

        return code
                .trim()
                .toUpperCase()
                .replace(' ', '_');
    }
}