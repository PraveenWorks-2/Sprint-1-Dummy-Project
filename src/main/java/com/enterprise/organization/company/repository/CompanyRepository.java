package com.enterprise.organization.company.repository;

import com.enterprise.organization.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyCodeIgnoreCase(String companyCode);
    Optional<Company> findByCompanyCodeIgnoreCase(String companyCode);
}
