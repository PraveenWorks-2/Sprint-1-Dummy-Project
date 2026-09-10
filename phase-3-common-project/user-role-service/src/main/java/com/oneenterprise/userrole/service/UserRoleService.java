package com.oneenterprise.userrole.service;

import com.oneenterprise.userrole.dto.UserRoleRequest;
import com.oneenterprise.userrole.dto.UserRoleResponse;

import java.util.List;

public interface UserRoleService {

    // Assign Role to User
    UserRoleResponse assignRoleToUser(UserRoleRequest request);

    // Remove User Role
    void removeUserRole(Long id);

    // Get all Roles assigned to a User
    List<UserRoleResponse> getUserRoles(Long userId);

    // Get User-Role mapping by ID
    UserRoleResponse getUserRoleById(Long id);

    // Get User Access Mapping
    List<UserRoleResponse> getUserAccessMapping(Long userId);
}