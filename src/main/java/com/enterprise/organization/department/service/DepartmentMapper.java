package com.enterprise.organization.department.service;

import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.businessunit.entity.BusinessUnit;
import com.enterprise.organization.department.dto.DepartmentRequestDTO;
import com.enterprise.organization.department.dto.DepartmentResponseDTO;
import com.enterprise.organization.department.entity.Department;
import com.enterprise.organization.department.entity.DepartmentStatus;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentRequestDTO dto, BusinessUnit businessUnit, Branch branch) {
        return Department.builder()
                .departmentCode(dto.getDepartmentCode())
                .departmentName(dto.getDepartmentName())
                .description(dto.getDescription())
                .businessUnit(businessUnit)
                .branch(branch)
                .status(DepartmentStatus.ACTIVE)
                .build();
    }

    public void updateEntity(
            Department department,
            DepartmentRequestDTO dto,
            BusinessUnit businessUnit,
            Branch branch) {
        department.setDepartmentCode(dto.getDepartmentCode());
        department.setDepartmentName(dto.getDepartmentName());
        department.setDescription(dto.getDescription());
        department.setBusinessUnit(businessUnit);
        department.setBranch(branch);
    }

    public DepartmentResponseDTO toResponseDTO(Department department) {
        return DepartmentResponseDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentCode(department.getDepartmentCode())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .businessUnitId(department.getBusinessUnit().getBusinessUnitId())
                .businessUnitName(department.getBusinessUnit().getBusinessUnitName())
                .branchId(department.getBranch().getBranchId())
                .branchName(department.getBranch().getBranchName())
                .status(department.getStatus())
                .createdAt(department.getCreatedAt())
                .updatedAt(department.getUpdatedAt())
                .build();
    }
}
