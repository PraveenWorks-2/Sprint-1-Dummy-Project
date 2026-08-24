package com.enterprise.organization.company.service;

import com.enterprise.organization.company.dto.CompanyRequestDTO;
import com.enterprise.organization.company.dto.CompanyResponseDTO;
import com.enterprise.organization.company.entity.Company;
import com.enterprise.organization.company.entity.CompanyStatus;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyRequestDTO dto) {
        return Company.builder()
                .companyCode(dto.getCompanyCode())
                .companyName(dto.getCompanyName())
                .industry(dto.getIndustry())
                .address(dto.getAddress())
                .contactEmail(dto.getContactEmail())
                .contactPhone(dto.getContactPhone())
                .status(CompanyStatus.ACTIVE)
                .build();
    }

    public void updateEntity(Company company, CompanyRequestDTO dto) {
        company.setCompanyCode(dto.getCompanyCode());
        company.setCompanyName(dto.getCompanyName());
        company.setIndustry(dto.getIndustry());
        company.setAddress(dto.getAddress());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());
    }

    public CompanyResponseDTO toResponseDTO(Company company) {
        return CompanyResponseDTO.builder()
                .companyId(company.getCompanyId())
                .companyCode(company.getCompanyCode())
                .companyName(company.getCompanyName())
                .industry(company.getIndustry())
                .address(company.getAddress())
                .contactEmail(company.getContactEmail())
                .contactPhone(company.getContactPhone())
                .status(company.getStatus())
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }
}
