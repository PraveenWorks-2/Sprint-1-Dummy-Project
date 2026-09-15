package com.example.permission_management_service.service;

import java.util.List;

import com.example.permission_management_service.dto.DepartmentPermissionRequest;
import com.example.permission_management_service.dto.DepartmentPermissionResponse;

public interface DepartmentPermissionService {

    DepartmentPermissionResponse assign(
            DepartmentPermissionRequest request);

    List<DepartmentPermissionResponse>
    getByDepartment(Long departmentId);

    List<DepartmentPermissionResponse>
    getActiveByDepartment(Long departmentId);

    boolean hasPermission(
            Long departmentId,
            String permissionCode);

    void remove(
            Long departmentId,
            Long permissionId);
}