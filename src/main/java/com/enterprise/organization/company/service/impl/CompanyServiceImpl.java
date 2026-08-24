package com.enterprise.organization.company.service.impl;

import com.enterprise.organization.common.exception.DuplicateResourceException;
import com.enterprise.organization.common.exception.ResourceNotFoundException;
import com.enterprise.organization.company.dto.CompanyRequestDTO;
import com.enterprise.organization.company.dto.CompanyResponseDTO;
import com.enterprise.organization.company.entity.Company;
import com.enterprise.organization.company.entity.CompanyStatus;
import com.enterprise.organization.company.repository.CompanyRepository;
import com.enterprise.organization.company.service.CompanyMapper;
import com.enterprise.organization.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyResponseDTO createCompany(CompanyRequestDTO requestDTO) {
        if (companyRepository.existsByCompanyCodeIgnoreCase(requestDTO.getCompanyCode())) {
            throw new DuplicateResourceException(
                    "Company already exists with companyCode: " + requestDTO.getCompanyCode());
        }
        Company saved = companyRepository.save(companyMapper.toEntity(requestDTO));
        return companyMapper.toResponseDTO(saved);
    }

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(companyMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CompanyResponseDTO getCompanyById(Long companyId) {
        return companyMapper.toResponseDTO(findCompanyOrThrow(companyId));
    }

    @Override
    @Transactional
    public CompanyResponseDTO updateCompany(Long companyId, CompanyRequestDTO requestDTO) {
        Company existing = findCompanyOrThrow(companyId);

        companyRepository.findByCompanyCodeIgnoreCase(requestDTO.getCompanyCode())
                .filter(other -> !other.getCompanyId().equals(companyId))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Another company already uses companyCode: " + requestDTO.getCompanyCode());
                });

        companyMapper.updateEntity(existing, requestDTO);
        return companyMapper.toResponseDTO(companyRepository.save(existing));
    }

    @Override
    @Transactional
    public CompanyResponseDTO updateCompanyStatus(Long companyId, String status) {
        Company existing = findCompanyOrThrow(companyId);
        CompanyStatus newStatus;
        try {
            newStatus = CompanyStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("status must be one of ACTIVE, INACTIVE");
        }
        existing.setStatus(newStatus);
        return companyMapper.toResponseDTO(companyRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteCompany(Long companyId) {
        Company existing = findCompanyOrThrow(companyId);
        companyRepository.delete(existing);
    }

    private Company findCompanyOrThrow(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
    }
}
