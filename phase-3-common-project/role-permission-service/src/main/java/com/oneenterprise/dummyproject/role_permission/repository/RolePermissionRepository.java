package com.oneenterprise.dummyproject.role_permission.repository;

import com.oneenterprise.dummyproject.role_permission.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRoleId(Long roleId);

    Optional<RolePermission> findByRoleIdAndPermissionId(Long roleId, Long permissionId);

    boolean existsByRoleIdAndPermissionId(Long roleId, Long permissionId);

    void deleteByRoleIdAndPermissionId(Long roleId, Long permissionId);

}
