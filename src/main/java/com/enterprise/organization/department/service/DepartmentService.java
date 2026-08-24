package com.enterprise.organization.department.service;

import com.enterprise.organization.department.dto.DepartmentRequestDTO;
import com.enterprise.organization.department.dto.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO);
    List<DepartmentResponseDTO> getAllDepartments();
    DepartmentResponseDTO getDepartmentById(Long departmentId);
    List<DepartmentResponseDTO> getDepartmentsByBusinessUnit(Long businessUnitId);
    List<DepartmentResponseDTO> getDepartmentsByBranch(Long branchId);
    DepartmentResponseDTO updateDepartment(Long departmentId, DepartmentRequestDTO requestDTO);
    DepartmentResponseDTO updateDepartmentStatus(Long departmentId, String status);
    void deleteDepartment(Long departmentId);
}
