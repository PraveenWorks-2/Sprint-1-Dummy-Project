package com.oneenterprise.userrole.controller;

import com.oneenterprise.userrole.dto.UserRoleRequest;
import com.oneenterprise.userrole.dto.UserRoleResponse;
import com.oneenterprise.userrole.service.UserRoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    // Assign Role to User
    @PostMapping
    public ResponseEntity<UserRoleResponse> assignRoleToUser(
            @Valid @RequestBody UserRoleRequest request) {

        UserRoleResponse response = userRoleService.assignRoleToUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Remove User Role
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUserRole(@PathVariable Long id) {

        userRoleService.removeUserRole(id);

        return ResponseEntity.ok("User role removed successfully");
    }

    // Get all Roles assigned to a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRoleResponse>> getUserRoles(
            @PathVariable Long userId) {

        List<UserRoleResponse> roles =
                userRoleService.getUserRoles(userId);

        return ResponseEntity.ok(roles);
    }

    // Get User-Role mapping by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserRoleResponse> getUserRoleById(
            @PathVariable Long id) {

        UserRoleResponse response =
                userRoleService.getUserRoleById(id);

        return ResponseEntity.ok(response);
    }

    // Get User Access Mapping
    @GetMapping("/access/{userId}")
    public ResponseEntity<List<UserRoleResponse>> getUserAccessMapping(
            @PathVariable Long userId) {

        List<UserRoleResponse> accessMapping =
                userRoleService.getUserAccessMapping(userId);

        return ResponseEntity.ok(accessMapping);
    }
}