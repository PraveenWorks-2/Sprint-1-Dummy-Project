package com.example.permission_management_service.service;

import java.util.List;

import com.example.permission_management_service.dto.PermissionMatrixRequest;
import com.example.permission_management_service.dto.PermissionMatrixResponse;

public interface PermissionMatrixService {

    PermissionMatrixResponse assign(
            PermissionMatrixRequest request);

    List<PermissionMatrixResponse>
    getByRole(String roleName);

    List<PermissionMatrixResponse>
    getActiveByRole(String roleName);

    boolean hasPermission(
            String roleName,
            String permissionCode);

    void remove(
            String roleName,
            Long permissionId);
}