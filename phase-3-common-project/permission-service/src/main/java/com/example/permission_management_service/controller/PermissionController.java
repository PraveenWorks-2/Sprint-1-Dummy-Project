package com.example.permission_management_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.permission_management_service.dto.PermissionRequest;
import com.example.permission_management_service.dto.PermissionResponse;
import com.example.permission_management_service.dto.PermissionUpdateRequest;
import com.example.permission_management_service.service.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@PostMapping
    public ResponseEntity<PermissionResponse> create(
            @Valid @RequestBody PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAll() {
        return ResponseEntity.ok(permissionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PermissionUpdateRequest request) {
        return ResponseEntity.ok(permissionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PermissionResponse> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(permissionService.deactivate(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PermissionResponse>> getByCategory(
            @PathVariable String category) {
        return ResponseEntity.ok(permissionService.getByCategory(category));
    }

    @GetMapping("/module/{module}")
    public ResponseEntity<List<PermissionResponse>> getByModule(
            @PathVariable String module) {
        return ResponseEntity.ok(permissionService.getByModule(module));
    }
}
