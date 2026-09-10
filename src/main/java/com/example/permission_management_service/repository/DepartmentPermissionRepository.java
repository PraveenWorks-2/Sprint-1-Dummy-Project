package com.example.permission_management_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.permission_management_service.entity.DepartmentPermission;

public interface DepartmentPermissionRepository
        extends JpaRepository<DepartmentPermission, Long> {

    List<DepartmentPermission>
    findByDepartmentId(Long departmentId);

    List<DepartmentPermission>
    findByDepartmentIdAndAllowedTrue(Long departmentId);

    Optional<DepartmentPermission>
    findByDepartmentIdAndPermissionId(
            Long departmentId,
            Long permissionId);

    boolean existsByDepartmentIdAndPermissionId(
            Long departmentId,
            Long permissionId);

    void deleteByDepartmentIdAndPermissionId(
            Long departmentId,
            Long permissionId);
}