package com.enterprise.organization.businessunit.repository;

import com.enterprise.organization.businessunit.entity.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {
    boolean existsByBusinessUnitCodeIgnoreCase(String businessUnitCode);
    Optional<BusinessUnit> findByBusinessUnitCodeIgnoreCase(String businessUnitCode);
    List<BusinessUnit> findByCompany_CompanyId(Long companyId);
}
