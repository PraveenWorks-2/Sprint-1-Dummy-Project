package com.oneenterprise.dummyproject.feature.management.service;

import java.util.List;

import com.oneenterprise.dummyproject.feature.management.dto.PlatformFeatureRequest;
import com.oneenterprise.dummyproject.feature.management.dto.PlatformFeatureResponse;

public interface PlatformFeatureService {

    PlatformFeatureResponse create(
            PlatformFeatureRequest request);

    List<PlatformFeatureResponse> getAll();

    PlatformFeatureResponse getById(Long id);

    PlatformFeatureResponse update(
            Long id,
            PlatformFeatureRequest request);

    void delete(Long id);
}