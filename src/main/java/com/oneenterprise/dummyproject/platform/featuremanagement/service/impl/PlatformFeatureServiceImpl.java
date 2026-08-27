package com.oneenterprise.dummyproject.platform.featuremanagement.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.platform.featuremanagement.dto.PlatformFeatureRequest;
import com.oneenterprise.dummyproject.platform.featuremanagement.dto.PlatformFeatureResponse;
import com.oneenterprise.dummyproject.platform.featuremanagement.entity.PlatformFeature;
import com.oneenterprise.dummyproject.platform.featuremanagement.repository.PlatformFeatureRepository;
import com.oneenterprise.dummyproject.platform.featuremanagement.service.PlatformFeatureService;

@Service
public class PlatformFeatureServiceImpl
        implements PlatformFeatureService {

    private final PlatformFeatureRepository platformfeatureRepository;

    public PlatformFeatureServiceImpl(
            PlatformFeatureRepository platformfeatureRepository) {

        this.platformfeatureRepository = platformfeatureRepository;
    }

    @Override
    public PlatformFeatureResponse create(
            PlatformFeatureRequest request) {

        PlatformFeature feature =
                new PlatformFeature();

        feature.setFeatureName(
                request.featureName());

        feature.setDescription(
                request.description());

        feature.setEnabled(
                request.enabled());

        return convert(platformfeatureRepository.save(feature));
    }

    @Override
    public List<PlatformFeatureResponse> getAll() {

        return platformfeatureRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public PlatformFeatureResponse getById(
            Long id) {

        PlatformFeature feature =
        		platformfeatureRepository.findById(id)
                        .orElseThrow();

        return convert(feature);
    }

    @Override
    public PlatformFeatureResponse update(
            Long id,
            PlatformFeatureRequest request) {

        PlatformFeature feature =
        		platformfeatureRepository.findById(id)
                        .orElseThrow();

        feature.setFeatureName(
                request.featureName());

        feature.setDescription(
                request.description());

        feature.setEnabled(
                request.enabled());

        return convert(platformfeatureRepository.save(feature));
    }

    @Override
    public void delete(Long id) {

        PlatformFeature feature =
        		platformfeatureRepository.findById(id)
                        .orElseThrow();

        platformfeatureRepository.delete(feature);
    }

    private PlatformFeatureResponse convert(
            PlatformFeature feature) {

        return new PlatformFeatureResponse(

                feature.getId(),

                feature.getFeatureName(),

                feature.getDescription(),

                feature.isEnabled()
        );
    }
}