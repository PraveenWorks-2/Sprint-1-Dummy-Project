package com.example.permission_management_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.permission_management_service.dto.DataPermissionRequest;
import com.example.permission_management_service.dto.DataPermissionResponse;
import com.example.permission_management_service.entity.DataPermission;
import com.example.permission_management_service.entity.Permission_Entity;
import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;
import com.example.permission_management_service.repository.DataPermissionRepository;
import com.example.permission_management_service.repository.PermissionRepository;

@Service
@Transactional
public class DataPermissionServiceImpl
        implements DataPermissionService {

    private final DataPermissionRepository repository;
    private final PermissionRepository permissionRepository;

    public DataPermissionServiceImpl(
            DataPermissionRepository repository,
            PermissionRepository permissionRepository) {

        this.repository = repository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public DataPermissionResponse create(
            DataPermissionRequest request) {

        if (repository.findByPermissionIdAndDataScope(
                request.getPermissionId(),
                request.getDataScope().trim().toUpperCase())
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Data permission already exists");
        }

        Permission_Entity permission =
                permissionRepository.findById(
                        request.getPermissionId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Permission not found"));

        DataPermission dataPermission =
                new DataPermission();

        dataPermission.setPermission(permission);

        dataPermission.setDataScope(
                request.getDataScope()
                        .trim()
                        .toUpperCase());

        dataPermission.setFilterExpression(
                request.getFilterExpression());

        dataPermission.setActive(true);

        return DataPermissionResponse.from(
                repository.save(dataPermission));
    }

    @Override
    public List<DataPermissionResponse>
    getByPermission(Long permissionId) {

        return repository.findByPermissionId(permissionId)
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

        return repository.findByActiveTrue()
                .stream()
                .map(DataPermissionResponse::from)
                .toList();
    }
}