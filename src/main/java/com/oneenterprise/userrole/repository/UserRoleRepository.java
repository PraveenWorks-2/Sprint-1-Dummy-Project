package com.oneenterprise.userrole.repository;
import com.oneenterprise.userrole.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUserId(Long userId);
    // Find a specific User + Role mapping
    Optional<UserRole> findByUserIdAndRoleId(
            Long userId,
            Long roleId);
    // Check whether User + Role mapping already exists
    boolean existsByUserIdAndRoleId(
            Long userId,
            Long roleId);
    // Check whether User + Role mapping exists
    // with a specific status such as ACTIVE
    boolean existsByUserIdAndRoleIdAndStatus(
            Long userId,
            Long roleId,
            String status);
    // Get only roles having a particular status
    // Example: ACTIVE
    List<UserRole> findByUserIdAndStatus(
            Long userId,
            String status);
    // Get all users having a particular role
    List<UserRole> findByRoleId(Long roleId);
}