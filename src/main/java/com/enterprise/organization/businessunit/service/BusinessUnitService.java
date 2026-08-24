package com.enterprise.organization.businessunit.service;

import com.enterprise.organization.businessunit.dto.BusinessUnitRequestDTO;
import com.enterprise.organization.businessunit.dto.BusinessUnitResponseDTO;

import java.util.List;

public interface BusinessUnitService {
    BusinessUnitResponseDTO createBusinessUnit(BusinessUnitRequestDTO requestDTO);
    List<BusinessUnitResponseDTO> getAllBusinessUnits();
    BusinessUnitResponseDTO getBusinessUnitById(Long businessUnitId);
    List<BusinessUnitResponseDTO> getBusinessUnitsByCompany(Long companyId);
    BusinessUnitResponseDTO updateBusinessUnit(Long businessUnitId, BusinessUnitRequestDTO requestDTO);
    BusinessUnitResponseDTO updateBusinessUnitStatus(Long businessUnitId, String status);
    void deleteBusinessUnit(Long businessUnitId);
}
