package com.oneenterprise.dummyproject.platform.superadmin.service.impl;

import com.oneenterprise.dummyproject.platform.common.exception.ResourceNotFoundException;
import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminRequestDto;
import com.oneenterprise.dummyproject.platform.superadmin.dto.SuperAdminResponseDto;
import com.oneenterprise.dummyproject.platform.superadmin.entity.SuperAdmin;
import com.oneenterprise.dummyproject.platform.superadmin.repository.SuperAdminRepository;
import com.oneenterprise.dummyproject.platform.superadmin.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    private final SuperAdminRepository superAdminRepository;

    @Autowired
    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }

    @Override
    public SuperAdminResponseDto createSuperAdmin(SuperAdminRequestDto request) {
        if (superAdminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Super Admin with email " + request.getEmail() + " already exists");
        }

        SuperAdmin admin = new SuperAdmin(request.getFullName(), request.getEmail(), request.getPhoneNumber());
        SuperAdmin saved = superAdminRepository.save(admin);
        return mapToDto(saved);
    }

    @Override
    public List<SuperAdminResponseDto> getAllSuperAdmins() {
        return superAdminRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SuperAdminResponseDto getSuperAdminById(Long id) {
        SuperAdmin admin = superAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Super Admin not found with id: " + id));
        return mapToDto(admin);
    }

    @Override
    public SuperAdminResponseDto toggleAdminStatus(Long id) {
        SuperAdmin admin = superAdminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Super Admin not found with id: " + id));
        admin.setIsActive(!admin.getIsActive());
        SuperAdmin updated = superAdminRepository.save(admin);
        return mapToDto(updated);
    }

    private SuperAdminResponseDto mapToDto(SuperAdmin admin) {
        return new SuperAdminResponseDto(
                admin.getId(),
                admin.getFullName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getIsActive(),
                admin.getCreatedAt()
        );
    }
}