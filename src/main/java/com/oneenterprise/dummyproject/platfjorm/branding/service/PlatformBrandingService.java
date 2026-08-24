package com.oneenterprise.dummyproject.platfjorm.branding.service;

import java.util.List;

import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingRequest;
import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingResponse;

public interface PlatformBrandingService {

    PlatformBrandingResponse create(
            PlatformBrandingRequest request);

    List<PlatformBrandingResponse> getAll();

    PlatformBrandingResponse getById(Long id);

    PlatformBrandingResponse update(
            Long id,
            PlatformBrandingRequest request);

    void delete(Long id);
}