package com.example.permission_management_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.permission_management_service.dto.DataPermissionRequest;
import com.example.permission_management_service.dto.DataPermissionResponse;
import com.example.permission_management_service.service.DataPermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/data-permissions")
public class DataPermissionController {

    private final DataPermissionService service;

    public DataPermissionController(
            DataPermissionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DataPermissionResponse> create(
            @Valid @RequestBody DataPermissionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping("/permission/{permissionId}")
    public ResponseEntity<List<DataPermissionResponse>>
    getByPermission(
            @PathVariable Long permissionId) {

        return ResponseEntity.ok(
                service.getByPermission(permissionId));
    }

    @GetMapping("/scope/{dataScope}")
    public ResponseEntity<List<DataPermissionResponse>>
    getByScope(
            @PathVariable String dataScope) {

        return ResponseEntity.ok(
                service.getByScope(dataScope));
    }

    @GetMapping("/active")
    public ResponseEntity<List<DataPermissionResponse>>
    getActive() {

        return ResponseEntity.ok(service.getActive());
    }
}