package com.example.permission_management_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.permission_management_service.entity.DataPermission;

public interface DataPermissionRepository
        extends JpaRepository<DataPermission, Long> {

    List<DataPermission>
    findByPermissionId(Long permissionId);

    List<DataPermission>
    findByDataScopeIgnoreCase(String dataScope);

    Optional<DataPermission>
    findByPermissionIdAndDataScope(
            Long permissionId,
            String dataScope);

    List<DataPermission>
    findByActiveTrue();
}