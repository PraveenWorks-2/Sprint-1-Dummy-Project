package com.example.permission_management_service.service;

import java.util.List;

import com.example.permission_management_service.dto.PermissionRequest;
import com.example.permission_management_service.dto.PermissionResponse;
import com.example.permission_management_service.dto.PermissionUpdateRequest;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    PermissionResponse getById(Long id);
    PermissionResponse update(Long id, PermissionUpdateRequest request);
    void delete(Long id);
    PermissionResponse deactivate(Long id);
    List<PermissionResponse> getByCategory(String category);
    List<PermissionResponse> getByModule(String module);
}
