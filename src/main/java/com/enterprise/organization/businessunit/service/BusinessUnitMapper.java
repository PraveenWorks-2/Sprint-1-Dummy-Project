package com.enterprise.organization.businessunit.service;

import com.enterprise.organization.businessunit.dto.BusinessUnitRequestDTO;
import com.enterprise.organization.businessunit.dto.BusinessUnitResponseDTO;
import com.enterprise.organization.businessunit.entity.BusinessUnit;
import com.enterprise.organization.businessunit.entity.BusinessUnitStatus;
import com.enterprise.organization.company.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class BusinessUnitMapper {

    public BusinessUnit toEntity(BusinessUnitRequestDTO dto, Company company) {
        return BusinessUnit.builder()
                .businessUnitCode(dto.getBusinessUnitCode())
                .businessUnitName(dto.getBusinessUnitName())
                .description(dto.getDescription())
                .company(company)
                .status(BusinessUnitStatus.ACTIVE)
                .build();
    }

    public void updateEntity(BusinessUnit businessUnit, BusinessUnitRequestDTO dto, Company company) {
        businessUnit.setBusinessUnitCode(dto.getBusinessUnitCode());
        businessUnit.setBusinessUnitName(dto.getBusinessUnitName());
        businessUnit.setDescription(dto.getDescription());
        businessUnit.setCompany(company);
    }

    public BusinessUnitResponseDTO toResponseDTO(BusinessUnit businessUnit) {
        return BusinessUnitResponseDTO.builder()
                .businessUnitId(businessUnit.getBusinessUnitId())
                .businessUnitCode(businessUnit.getBusinessUnitCode())
                .businessUnitName(businessUnit.getBusinessUnitName())
                .description(businessUnit.getDescription())
                .companyId(businessUnit.getCompany().getCompanyId())
                .companyName(businessUnit.getCompany().getCompanyName())
                .status(businessUnit.getStatus())
                .createdAt(businessUnit.getCreatedAt())
                .updatedAt(businessUnit.getUpdatedAt())
                .build();
    }
}
