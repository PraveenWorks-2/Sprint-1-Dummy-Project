package com.oneenterprise.dummyproject.platform.featuremanagement.service;

import java.util.List;

import com.oneenterprise.dummyproject.platform.featuremanagement.dto.PlatformFeatureRequest;
import com.oneenterprise.dummyproject.platform.featuremanagement.dto.PlatformFeatureResponse;

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