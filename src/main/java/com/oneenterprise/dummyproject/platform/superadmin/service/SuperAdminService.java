package com.oneenterprise.dummyproject.platform.superadmin.service;

import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminRequestDto;
import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminResponseDto;

import java.util.List;

public interface SuperAdminService {
    SuperAdminResponseDto createSuperAdmin(SuperAdminRequestDto request);
    List<SuperAdminResponseDto> getAllSuperAdmins();
    SuperAdminResponseDto getSuperAdminById(Long id);
    SuperAdminResponseDto toggleAdminStatus(Long id);
}