package com.enterprise.organization.department.service.impl;

import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.branch.repository.BranchRepository;
import com.enterprise.organization.businessunit.entity.BusinessUnit;
import com.enterprise.organization.businessunit.repository.BusinessUnitRepository;
import com.enterprise.organization.common.exception.DuplicateResourceException;
import com.enterprise.organization.common.exception.ResourceNotFoundException;
import com.enterprise.organization.department.dto.DepartmentRequestDTO;
import com.enterprise.organization.department.dto.DepartmentResponseDTO;
import com.enterprise.organization.department.entity.Department;
import com.enterprise.organization.department.entity.DepartmentStatus;
import com.enterprise.organization.department.repository.DepartmentRepository;
import com.enterprise.organization.department.service.DepartmentMapper;
import com.enterprise.organization.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final BusinessUnitRepository businessUnitRepository;
    private final BranchRepository branchRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    @Transactional
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO) {
        if (departmentRepository.existsByDepartmentCodeIgnoreCase(requestDTO.getDepartmentCode())) {
            throw new DuplicateResourceException(
                    "Department already exists with departmentCode: " + requestDTO.getDepartmentCode());
        }

        BusinessUnit businessUnit = findBusinessUnitOrThrow(requestDTO.getBusinessUnitId());
        Branch branch = findBranchOrThrow(requestDTO.getBranchId());

        Department saved = departmentRepository.save(
                departmentMapper.toEntity(requestDTO, businessUnit, branch));
        return departmentMapper.toResponseDTO(saved);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(Long departmentId) {
        return departmentMapper.toResponseDTO(findDepartmentOrThrow(departmentId));
    }

    @Override
    public List<DepartmentResponseDTO> getDepartmentsByBusinessUnit(Long businessUnitId) {
        findBusinessUnitOrThrow(businessUnitId);
        return departmentRepository.findByBusinessUnit_BusinessUnitId(businessUnitId).stream()
                .map(departmentMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<DepartmentResponseDTO> getDepartmentsByBranch(Long branchId) {
        findBranchOrThrow(branchId);
        return departmentRepository.findByBranch_BranchId(branchId).stream()
                .map(departmentMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public DepartmentResponseDTO updateDepartment(Long departmentId, DepartmentRequestDTO requestDTO) {
        Department existing = findDepartmentOrThrow(departmentId);

        departmentRepository.findByDepartmentCodeIgnoreCase(requestDTO.getDepartmentCode())
                .filter(other -> !other.getDepartmentId().equals(departmentId))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Another department already uses departmentCode: " + requestDTO.getDepartmentCode());
                });

        BusinessUnit businessUnit = findBusinessUnitOrThrow(requestDTO.getBusinessUnitId());
        Branch branch = findBranchOrThrow(requestDTO.getBranchId());

        departmentMapper.updateEntity(existing, requestDTO, businessUnit, branch);
        return departmentMapper.toResponseDTO(departmentRepository.save(existing));
    }

    @Override
    @Transactional
    public DepartmentResponseDTO updateDepartmentStatus(Long departmentId, String status) {
        Department existing = findDepartmentOrThrow(departmentId);
        DepartmentStatus newStatus;
        try {
            newStatus = DepartmentStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentException("status must be one of ACTIVE, INACTIVE");
        }
        existing.setStatus(newStatus);
        return departmentMapper.toResponseDTO(departmentRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteDepartment(Long departmentId) {
        Department existing = findDepartmentOrThrow(departmentId);
        departmentRepository.delete(existing);
    }

    private Department findDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));
    }

    private BusinessUnit findBusinessUnitOrThrow(Long businessUnitId) {
        return businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Business unit not found with id: " + businessUnitId));
    }

    private Branch findBranchOrThrow(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Branch not found with id: " + branchId));
    }
}
