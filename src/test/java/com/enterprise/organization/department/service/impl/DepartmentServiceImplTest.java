package com.enterprise.organization.department.service.impl;

import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.branch.repository.BranchRepository;
import com.enterprise.organization.businessunit.entity.BusinessUnit;
import com.enterprise.organization.businessunit.repository.BusinessUnitRepository;
import com.enterprise.organization.department.dto.DepartmentRequestDTO;
import com.enterprise.organization.department.entity.Department;
import com.enterprise.organization.department.repository.DepartmentRepository;
import com.enterprise.organization.department.service.DepartmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private BusinessUnitRepository businessUnitRepository;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @Test
    void createDepartment_shouldValidateBothParents() {
        DepartmentRequestDTO request = new DepartmentRequestDTO();
        request.setDepartmentCode("ENG");
        request.setDepartmentName("Engineering");
        request.setBusinessUnitId(1L);
        request.setBranchId(2L);

        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setBusinessUnitId(1L);

        Branch branch = new Branch();
        branch.setBranchId(2L);

        Department department = new Department();

        when(departmentRepository.existsByDepartmentCodeIgnoreCase("ENG")).thenReturn(false);
        when(businessUnitRepository.findById(1L)).thenReturn(Optional.of(businessUnit));
        when(branchRepository.findById(2L)).thenReturn(Optional.of(branch));
        when(departmentMapper.toEntity(request, businessUnit, branch)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(department);

        departmentService.createDepartment(request);

        verify(departmentRepository).save(department);
    }

    @Test
    void createDepartment_shouldThrowWhenBusinessUnitMissing() {
        DepartmentRequestDTO request = new DepartmentRequestDTO();
        request.setDepartmentCode("ENG");
        request.setDepartmentName("Engineering");
        request.setBusinessUnitId(99L);
        request.setBranchId(2L);

        when(departmentRepository.existsByDepartmentCodeIgnoreCase("ENG")).thenReturn(false);
        when(businessUnitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> departmentService.createDepartment(request));
    }
}
