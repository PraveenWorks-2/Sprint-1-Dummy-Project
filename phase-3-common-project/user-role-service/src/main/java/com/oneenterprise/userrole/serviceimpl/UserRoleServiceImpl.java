

package com.oneenterprise.userrole.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.oneenterprise.userrole.dto.UserRoleRequest;
import com.oneenterprise.userrole.dto.UserRoleResponse;
import com.oneenterprise.userrole.entity.UserRole;
import com.oneenterprise.userrole.repository.UserRoleRepository;
import com.oneenterprise.userrole.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    // 1. Assign Role to User
    @Override
    public UserRoleResponse assignRoleToUser(UserRoleRequest request) {

        UserRole userRole = new UserRole();

        userRole.setUserId(request.getUserId());
        userRole.setRoleId(request.getRoleId());

        // Set default values
        userRole.setAssignedAt(LocalDateTime.now());
        userRole.setStatus("ACTIVE");

        UserRole savedUserRole = userRoleRepository.save(userRole);

        return convertToResponse(savedUserRole);
    }

    // 2. Get User Role by ID
    @Override
    public UserRoleResponse getUserRoleById(Long id) {

        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User role not found with id: " + id));

        return convertToResponse(userRole);
    }

    // 3. Get All Roles for a User
    @Override
    public List<UserRoleResponse> getUserRoles(Long userId) {

        List<UserRole> userRoles =
                userRoleRepository.findByUserId(userId);

        return userRoles.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // 4. Remove User Role
    @Override
    public void removeUserRole(Long id) {

        if (!userRoleRepository.existsById(id)) {
            throw new RuntimeException(
                    "User role not found with id: " + id);
        }

        userRoleRepository.deleteById(id);
    }

    // 5. Get User Access Mapping
    @Override
    public List<UserRoleResponse> getUserAccessMapping(Long userId) {

        List<UserRole> userRoles =
                userRoleRepository.findByUserId(userId);

        return userRoles.stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Convert Entity to Response DTO
    private UserRoleResponse convertToResponse(UserRole userRole) {

        UserRoleResponse response = new UserRoleResponse();

        response.setId(userRole.getId());
        response.setUserId(userRole.getUserId());
        response.setRoleId(userRole.getRoleId());
        response.setAssignedAt(userRole.getAssignedAt());
        response.setStatus(userRole.getStatus());

        return response;
    }
}

