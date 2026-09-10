package com.oneenterprise.userrole.repository;

import com.oneenterprise.userrole.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    // Get all roles assigned to a user
    List<UserRole> findByUserId(Long userId);

    // Find a specific user-role mapping
    Optional<UserRole> findByUserIdAndRoleId(Long userId, Long roleId);

    // Check whether a role is already assigned to a user
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    // Get all active roles assigned to a user
    List<UserRole> findByUserIdAndStatus(Long userId, String status);

    // Get all users assigned to a particular role
    List<UserRole> findByRoleId(Long roleId);
}