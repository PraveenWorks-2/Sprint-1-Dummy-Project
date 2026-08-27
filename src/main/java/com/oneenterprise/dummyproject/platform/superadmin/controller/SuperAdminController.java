package com.oneenterprise.dummyproject.platform.superadmin.controller;

import com.oneenterprise.dummyproject.platform.common.response.ApiResponse;
import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminRequestDto;
import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminResponseDto;
import com.oneenterprise.dummyproject.platform.superadmin.service.SuperAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/superadmins")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

	public SuperAdminController(SuperAdminService superAdminService) {
		this.superAdminService = superAdminService;
	}

	@PostMapping
    public ResponseEntity<ApiResponse<SuperAdminResponseDto>> createSuperAdmin(@RequestBody SuperAdminRequestDto request) {
        SuperAdminResponseDto response = superAdminService.createSuperAdmin(request);
        return new ResponseEntity<>(ApiResponse.success("Super Admin created successfully", response), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SuperAdminResponseDto>>> getAllSuperAdmins() {
        List<SuperAdminResponseDto> response = superAdminService.getAllSuperAdmins();
        return ResponseEntity.ok(ApiResponse.success("Super Admins fetched successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SuperAdminResponseDto>> getSuperAdminById(@PathVariable Long id) {
        SuperAdminResponseDto response = superAdminService.getSuperAdminById(id);
        return ResponseEntity.ok(ApiResponse.success("Super Admin fetched successfully", response));
    }

    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<SuperAdminResponseDto>> toggleStatus(@PathVariable Long id) {
        SuperAdminResponseDto response = superAdminService.toggleAdminStatus(id);
        return ResponseEntity.ok(ApiResponse.success("Super Admin status toggled successfully", response));
    }
}