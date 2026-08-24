package com.enterprise.organization.department.repository;

import com.enterprise.organization.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    boolean existsByDepartmentCodeIgnoreCase(String departmentCode);
    Optional<Department> findByDepartmentCodeIgnoreCase(String departmentCode);
    List<Department> findByBusinessUnit_BusinessUnitId(Long businessUnitId);
    List<Department> findByBranch_BranchId(Long branchId);
}
