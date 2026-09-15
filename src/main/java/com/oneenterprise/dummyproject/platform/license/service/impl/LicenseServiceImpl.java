package com.oneenterprise.dummyproject.platform.license.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.platform.common.exception.ResourceNotFoundException;
import com.oneenterprise.dummyproject.platform.license.dto.LicenseRequest;
import com.oneenterprise.dummyproject.platform.license.dto.LicenseResponse;
import com.oneenterprise.dummyproject.platform.license.entity.License;
import com.oneenterprise.dummyproject.platform.license.repository.LicenseRepository;
import com.oneenterprise.dummyproject.platform.license.service.LicenseService;

@Service
public class LicenseServiceImpl
        implements LicenseService {

    private final LicenseRepository licenseRepository;

    public LicenseServiceImpl(
            LicenseRepository licenseRepository) {

        this.licenseRepository = licenseRepository;
    }

    @Override
    public LicenseResponse create(
            LicenseRequest request) {

        License license = new License();

        license.setLicenseKey(
                request.licenseKey());

        license.setLicenseType(
                request.licenseType());

        license.setStartDate(
                request.startDate());

        license.setExpiryDate(
                request.expiryDate());

        license.setActive(
                request.active());

        return convert(licenseRepository.save(license));
    }

    @Override
    public List<LicenseResponse> getAll() {

        return licenseRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public LicenseResponse getById(Long id) {

        License license =
        		licenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "License not found: " + id));

        return convert(license);
    }

    @Override
    public LicenseResponse update(
            Long id,
            LicenseRequest request) {

        License license =
        		licenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "License not found: " + id));

        license.setLicenseKey(
                request.licenseKey());

        license.setLicenseType(
                request.licenseType());

        license.setStartDate(
                request.startDate());

        license.setExpiryDate(
                request.expiryDate());

        license.setActive(
                request.active());

        return convert(licenseRepository.save(license));
    }

    @Override
    public void delete(Long id) {

        License license =
        		licenseRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "License not found: " + id));

        licenseRepository.delete(license);
    }

    private LicenseResponse convert(
            License license) {

        return new LicenseResponse(

                license.getId(),

                license.getLicenseKey(),

                license.getLicenseType(),

                license.getStartDate(),

                license.getExpiryDate(),

                license.isActive()
        );
    }
}