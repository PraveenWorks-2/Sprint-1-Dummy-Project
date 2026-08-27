package com.oneenterprise.dummyproject.role.repository;

import com.oneenterprise.dummyproject.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleNameAndTenantId(String roleName, String tenantId);
    boolean existsByRoleNameAndTenantId(String roleName, String tenantId);
    List<Role> findByTenantId(String tenantId);
    List<Role> findByTenantIdAndIsActiveTrue(String tenantId);
    List<Role> findByTenantIdAndIsCustom(String tenantId, Boolean isCustom);
}