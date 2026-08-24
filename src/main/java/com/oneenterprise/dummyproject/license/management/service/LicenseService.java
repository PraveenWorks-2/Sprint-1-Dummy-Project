package com.oneenterprise.dummyproject.license.management.service;

import java.util.List;

import com.oneenterprise.dummyproject.license.management.dto.LicenseRequest;
import com.oneenterprise.dummyproject.license.management.dto.LicenseResponse;

public interface LicenseService {

    LicenseResponse create(
            LicenseRequest request);

    List<LicenseResponse> getAll();

    LicenseResponse getById(Long id);

    LicenseResponse update(
            Long id,
            LicenseRequest request);

    void delete(Long id);
}
