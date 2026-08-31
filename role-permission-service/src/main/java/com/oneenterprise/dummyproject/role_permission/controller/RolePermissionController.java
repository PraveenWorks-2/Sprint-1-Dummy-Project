package com.oneenterprise.dummyproject.role_permission.controller;

import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionRequestDto;
import com.oneenterprise.dummyproject.role_permission.dto.RolePermissionResponseDto;
import com.oneenterprise.dummyproject.role_permission.service.RolePermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/role-permission")
@RequiredArgsConstructor

public class RolePermissionController {

        private final RolePermissionService rolePermissionService;

        @PostMapping
        public ResponseEntity<RolePermissionResponseDto> assignPermissionToRole(
                @Valid @RequestBody RolePermissionRequestDto requestDto) {

            RolePermissionResponseDto response = rolePermissionService.assignPermissionToRole(requestDto);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @DeleteMapping
        public ResponseEntity<Void> removePermissionFromRole(
                @RequestParam Long roleId,
                @RequestParam Long permissionId) {

            rolePermissionService.removePermissionFromRole(roleId, permissionId);

            return ResponseEntity.noContent().build();
        }

        @GetMapping("/role/{roleId}")
        public ResponseEntity<List<RolePermissionResponseDto>> getPermissionsByRole(
                @PathVariable Long roleId) {

            List<RolePermissionResponseDto> response = rolePermissionService.getPermissionsByRole(roleId);

            return ResponseEntity.ok(response);
        }

        @GetMapping("/matrix")
        public ResponseEntity<List<RolePermissionResponseDto>> getPermissionMatrix() {

            List<RolePermissionResponseDto> response = rolePermissionService.getPermissionMatrix();

            return ResponseEntity.ok(response);
        }

}
