package com.example.permission_management_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.permission_management_service.entity.PermissionMatrix;

public interface PermissionMatrixRepository
        extends JpaRepository<PermissionMatrix, Long> {

    List<PermissionMatrix>
    findByRoleNameIgnoreCase(String roleName);

    List<PermissionMatrix>
    findByRoleNameIgnoreCaseAndAllowedTrue(String roleName);

    Optional<PermissionMatrix>
    findByRoleNameIgnoreCaseAndPermissionId(
            String roleName,
            Long permissionId);

    boolean existsByRoleNameIgnoreCaseAndPermissionId(
            String roleName,
            Long permissionId);
}