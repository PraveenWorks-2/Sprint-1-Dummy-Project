package com.oneenterprise.dummyproject.platform.brandng.service.impl;

import org.springframework.stereotype.Service;

import com.oneenterprise.dummyproject.platfjorm.branding.service.PlatformBrandingService;
import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingRequest;
import com.oneenterprise.dummyproject.platform.branding.dto.PlatformBrandingResponse;
import com.oneenterprise.dummyproject.platform.branding.entity.Platform_Entity;
import com.oneenterprise.dummyproject.platform.branding.repository.PlatformBrandingRepository;
import com.oneenterprise.dummyproject.platform.common.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class PlatformBrandingServiceImpl
        implements PlatformBrandingService {

    private final PlatformBrandingRepository platformbrandingRepository;

    public PlatformBrandingServiceImpl(
            PlatformBrandingRepository platformbrandingRepository) {

        this.platformbrandingRepository = platformbrandingRepository;
    }

    @Override
    public PlatformBrandingResponse create(
            PlatformBrandingRequest request) {

    	Platform_Entity branding =
                new Platform_Entity();

        branding.setPlatformName(
                request.platformName());

        branding.setLogoUrl(
                request.logoUrl());

        branding.setPrimaryColor(
                request.primaryColor());

        branding.setSecondaryColor(
                request.secondaryColor());

        branding.setFaviconUrl(
                request.faviconUrl());

        return convert(platformbrandingRepository.save(branding));
    }

    @Override
    public List<PlatformBrandingResponse> getAll() {

        return platformbrandingRepository.findAll()
                .stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public PlatformBrandingResponse getById(
            Long id) {

    	Platform_Entity branding =
    			platformbrandingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branding not found: " + id));

        return convert(branding);
    }

    @Override
    public PlatformBrandingResponse update(
            Long id,
            PlatformBrandingRequest request) {

    	Platform_Entity branding =
    			platformbrandingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branding not found: " + id));

        branding.setPlatformName(
                request.platformName());

        branding.setLogoUrl(
                request.logoUrl());

        branding.setPrimaryColor(
                request.primaryColor());

        branding.setSecondaryColor(
                request.secondaryColor());

        branding.setFaviconUrl(
                request.faviconUrl());

        return convert(platformbrandingRepository.save(branding));
    }

    @Override
    public void delete(Long id) {

    	Platform_Entity branding =
    			platformbrandingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Branding not found: " + id));

    	platformbrandingRepository.delete(branding);
    }

    private PlatformBrandingResponse convert(
            Platform_Entity branding) {

        return new PlatformBrandingResponse(

                branding.getId(),

                branding.getPlatformName(),

                branding.getLogoUrl(),

                branding.getPrimaryColor(),

                branding.getSecondaryColor(),

                branding.getFaviconUrl()
        );
    }
}
