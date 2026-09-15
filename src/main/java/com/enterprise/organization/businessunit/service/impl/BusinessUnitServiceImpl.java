package com.enterprise.organization.businessunit.service.impl;

import com.enterprise.organization.businessunit.dto.BusinessUnitRequestDTO;
import com.enterprise.organization.businessunit.dto.BusinessUnitResponseDTO;
import com.enterprise.organization.businessunit.entity.BusinessUnit;
import com.enterprise.organization.businessunit.entity.BusinessUnitStatus;
import com.enterprise.organization.businessunit.repository.BusinessUnitRepository;
import com.enterprise.organization.businessunit.service.BusinessUnitMapper;
import com.enterprise.organization.businessunit.service.BusinessUnitService;
import com.enterprise.organization.common.exception.DuplicateResourceException;
import com.enterprise.organization.common.exception.ResourceNotFoundException;
import com.enterprise.organization.company.entity.Company;
import com.enterprise.organization.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessUnitServiceImpl implements BusinessUnitService {

    private final BusinessUnitRepository businessUnitRepository;
    private final CompanyRepository companyRepository;
    private final BusinessUnitMapper businessUnitMapper;

    @Override
    @Transactional
    public BusinessUnitResponseDTO createBusinessUnit(BusinessUnitRequestDTO requestDTO) {
        if (businessUnitRepository.existsByBusinessUnitCodeIgnoreCase(requestDTO.getBusinessUnitCode())) {
            throw new DuplicateResourceException(
                    "Business unit already exists with businessUnitCode: " + requestDTO.getBusinessUnitCode());
        }
        Company company = findCompanyOrThrow(requestDTO.getCompanyId());
        BusinessUnit saved = businessUnitRepository.save(businessUnitMapper.toEntity(requestDTO, company));
        return businessUnitMapper.toResponseDTO(saved);
    }

    @Override
    public List<BusinessUnitResponseDTO> getAllBusinessUnits() {
        return businessUnitRepository.findAll().stream()
                .map(businessUnitMapper::toResponseDTO)
                .toList();
    }

    @Override
    public BusinessUnitResponseDTO getBusinessUnitById(Long businessUnitId) {
        return businessUnitMapper.toResponseDTO(findBusinessUnitOrThrow(businessUnitId));
    }

    @Override
    public List<BusinessUnitResponseDTO> getBusinessUnitsByCompany(Long companyId) {
        findCompanyOrThrow(companyId);
        return businessUnitRepository.findByCompany_CompanyId(companyId).stream()
                .map(businessUnitMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public BusinessUnitResponseDTO updateBusinessUnit(Long businessUnitId, BusinessUnitRequestDTO requestDTO) {
        BusinessUnit existing = findBusinessUnitOrThrow(businessUnitId);

        businessUnitRepository.findByBusinessUnitCodeIgnoreCase(requestDTO.getBusinessUnitCode())
                .filter(other -> !other.getBusinessUnitId().equals(businessUnitId))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Another business unit already uses businessUnitCode: " + requestDTO.getBusinessUnitCode());
                });

        Company company = findCompanyOrThrow(requestDTO.getCompanyId());
        businessUnitMapper.updateEntity(existing, requestDTO, company);
        return businessUnitMapper.toResponseDTO(businessUnitRepository.save(existing));
    }

    @Override
    @Transactional
    public BusinessUnitResponseDTO updateBusinessUnitStatus(Long businessUnitId, String status) {
        BusinessUnit existing = findBusinessUnitOrThrow(businessUnitId);
        BusinessUnitStatus newStatus;
        try {
            newStatus = BusinessUnitStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("status must be one of ACTIVE, INACTIVE");
        }
        existing.setStatus(newStatus);
        return businessUnitMapper.toResponseDTO(businessUnitRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteBusinessUnit(Long businessUnitId) {
        BusinessUnit existing = findBusinessUnitOrThrow(businessUnitId);
        businessUnitRepository.delete(existing);
    }

    private BusinessUnit findBusinessUnitOrThrow(Long businessUnitId) {
        return businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new ResourceNotFoundException("Business unit not found with id: " + businessUnitId));
    }

    private Company findCompanyOrThrow(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
    }
}
