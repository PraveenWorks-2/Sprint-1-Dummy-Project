package com.example.permission_management_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.permission_management_service.dto.PermissionRequest;
import com.example.permission_management_service.dto.PermissionResponse;
import com.example.permission_management_service.dto.PermissionUpdateRequest;
import com.example.permission_management_service.entity.Permission_Entity;
import com.example.permission_management_service.exception.DuplicateResourceException;
import com.example.permission_management_service.exception.ResourceNotFoundException;
import com.example.permission_management_service.repository.PermissionRepository;

import jakarta.transaction.Transactional;
import lombok.Builder;

@Service
@Transactional
@Builder
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;

    public PermissionServiceImpl(PermissionRepository repository) {
		this.repository = repository;
	}

	@Override
    public PermissionResponse create(PermissionRequest request) {
        String code = normalizeCode(request.getCode());

        if (repository.existsByCode(code)) {
            throw new DuplicateResourceException(
                    "Permission already exists with code: " + code);
        }

//        Permission_Entity permission = Permission_Entity.builder()
//                .name(request.getName().trim())
//                .code(request.getCode())
//                .description(request.getDescription())
//                .category(request.getCategory().trim().toUpperCase())
//                .module(request.getModule().trim())
//                .active(true)
//                .build();
        
        Permission_Entity permission = new Permission_Entity();

        permission.setName(request.getName());
        permission.setCode(request.getCode());
        permission.setCategory(request.getCategory());
        permission.setModule(request.getModule());
        permission.setDescription(request.getDescription());
        permission.setActive(true);

        return PermissionResponse.from(repository.save(permission));
    }

    @Override
    public List<PermissionResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    @Override
    public PermissionResponse getById(Long id) {
        return PermissionResponse.from(findPermission(id));
    }

    @Override
    public PermissionResponse update(Long id, PermissionUpdateRequest request) {
    	Permission_Entity permission = findPermission(id);

        permission.setName(request.getName().trim());
        permission.setDescription(request.getDescription());
        permission.setCategory(request.getCategory().trim().toUpperCase());
        permission.setModule(request.getModule().trim());

        if (request.getActive() != null) {
            permission.setActive(request.getActive());
        }

        return PermissionResponse.from(repository.save(permission));
    }

    @Override
    public void delete(Long id) {
        Permission_Entity permission = findPermission(id);
        repository.delete(permission);
    }

    @Override
    public PermissionResponse deactivate(Long id) {
    	Permission_Entity permission = findPermission(id);
        permission.setActive(false);
        return PermissionResponse.from(repository.save(permission));
    }

    @Override
    public List<PermissionResponse> getByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category)
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    @Override
    public List<PermissionResponse> getByModule(String module) {
        return repository.findByModuleIgnoreCase(module)
                .stream()
                .map(PermissionResponse::from)
                .toList();
    }

    private Permission_Entity findPermission(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Permission not found with id: " + id));
    }

    private String normalizeCode(String code) {
        return code.trim().toUpperCase().replace(' ', '_');
    }
}

