package com.enterprise.organization.company.service;

import com.enterprise.organization.company.dto.CompanyRequestDTO;
import com.enterprise.organization.company.dto.CompanyResponseDTO;

import java.util.List;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyRequestDTO requestDTO);
    List<CompanyResponseDTO> getAllCompanies();
    CompanyResponseDTO getCompanyById(Long companyId);
    CompanyResponseDTO updateCompany(Long companyId, CompanyRequestDTO requestDTO);
    CompanyResponseDTO updateCompanyStatus(Long companyId, String status);
    void deleteCompany(Long companyId);
}
