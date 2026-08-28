package com.oneenterprise.roleservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oneenterprise.roleservice.dto.RoleRequestDto;
import com.oneenterprise.roleservice.dto.RoleResponseDto;
import com.oneenterprise.roleservice.dto.RoleUpdateDto;
import com.oneenterprise.roleservice.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDto> createRole(@Valid @RequestBody RoleRequestDto requestDto) {
        RoleResponseDto created = roleService.createRole(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles(
            @RequestParam String tenantId,
            @RequestParam(required = false, defaultValue = "false") Boolean activeOnly) {
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
    public ResponseEntity<String> deactivateRole(@PathVariable Long id) {
        roleService.deactivateRole(id);
        return ResponseEntity.ok("Role deactivated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted successfully.");
    }
}