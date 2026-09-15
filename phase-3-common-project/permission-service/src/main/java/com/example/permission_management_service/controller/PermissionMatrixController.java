package com.example.permission_management_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.permission_management_service.dto.PermissionMatrixRequest;
import com.example.permission_management_service.dto.PermissionMatrixResponse;
import com.example.permission_management_service.service.PermissionMatrixService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permission-matrix")
public class PermissionMatrixController {

    private final PermissionMatrixService service;

    public PermissionMatrixController(
            PermissionMatrixService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PermissionMatrixResponse> assign(
            @Valid @RequestBody PermissionMatrixRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.assign(request));
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<PermissionMatrixResponse>>
    getByRole(
            @PathVariable String roleName) {

        return ResponseEntity.ok(
                service.getByRole(roleName));
    }

    @GetMapping("/role/{roleName}/active")
    public ResponseEntity<List<PermissionMatrixResponse>>
    getActiveByRole(
            @PathVariable String roleName) {

        return ResponseEntity.ok(
                service.getActiveByRole(roleName));
    }

    @GetMapping("/role/{roleName}/check/{permissionCode}")
    public ResponseEntity<Boolean> check(
            @PathVariable String roleName,
            @PathVariable String permissionCode) {

        return ResponseEntity.ok(
                service.hasPermission(
                        roleName,
                        permissionCode));
    }

    @DeleteMapping("/role/{roleName}/permission/{permissionId}")
    public ResponseEntity<Void> remove(
            @PathVariable String roleName,
            @PathVariable Long permissionId) {

        service.remove(roleName, permissionId);

        return ResponseEntity.noContent().build();
    }
}