package com.oneenterprise.roleservice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oneenterprise.roleservice.dto.RoleRequestDto;
import com.oneenterprise.roleservice.dto.RoleResponseDto;
import com.oneenterprise.roleservice.dto.RoleUpdateDto;
import com.oneenterprise.roleservice.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto requestDto) {
        return new ResponseEntity<>(roleService.createRole(requestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(
            @RequestParam String tenantId,
            @RequestParam(required = false) Boolean activeOnly) {
        return ResponseEntity.ok(roleService.getAllRoles(tenantId, activeOnly));
    }

    @GetMapping("/custom")
    public ResponseEntity<List<RoleResponseDto>> getCustomRoles(@RequestParam String tenantId) {
        return ResponseEntity.ok(roleService.getCustomRoles(tenantId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateDto updateDto) {
        return ResponseEntity.ok(roleService.updateRole(id, updateDto));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateRole(@PathVariable Long id) {
        roleService.deactivateRole(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}