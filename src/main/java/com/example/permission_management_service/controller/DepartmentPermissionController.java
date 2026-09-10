package com.example.permission_management_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.permission_management_service.dto.DepartmentPermissionRequest;
import com.example.permission_management_service.dto.DepartmentPermissionResponse;
import com.example.permission_management_service.service.DepartmentPermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/department-permissions")
public class DepartmentPermissionController {

    private final DepartmentPermissionService service;

    public DepartmentPermissionController(
            DepartmentPermissionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DepartmentPermissionResponse> assign(
            @Valid @RequestBody DepartmentPermissionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.assign(request));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DepartmentPermissionResponse>>
    getByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                service.getByDepartment(departmentId));
    }

    @GetMapping("/department/{departmentId}/active")
    public ResponseEntity<List<DepartmentPermissionResponse>>
    getActive(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                service.getActiveByDepartment(departmentId));
    }

    @GetMapping("/department/{departmentId}/check/{permissionCode}")
    public ResponseEntity<Boolean> check(
            @PathVariable Long departmentId,
            @PathVariable String permissionCode) {

        return ResponseEntity.ok(
                service.hasPermission(
                        departmentId,
                        permissionCode));
    }

    @DeleteMapping(
            "/department/{departmentId}/permission/{permissionId}")
    public ResponseEntity<Void> remove(
            @PathVariable Long departmentId,
            @PathVariable Long permissionId) {

        service.remove(departmentId, permissionId);

        return ResponseEntity.noContent().build();
    }
}