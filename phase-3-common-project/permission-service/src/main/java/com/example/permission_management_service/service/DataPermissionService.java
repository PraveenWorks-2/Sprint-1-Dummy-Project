package com.example.permission_management_service.service;

import java.util.List;

import com.example.permission_management_service.dto.DataPermissionRequest;
import com.example.permission_management_service.dto.DataPermissionResponse;

public interface DataPermissionService {

    DataPermissionResponse create(
            DataPermissionRequest request);

    List<DataPermissionResponse>
    getByPermission(Long permissionId);

    List<DataPermissionResponse>
    getByScope(String dataScope);

    List<DataPermissionResponse>
    getActive();
}