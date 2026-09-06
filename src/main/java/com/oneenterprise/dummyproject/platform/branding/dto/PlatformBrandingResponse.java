package com.oneenterprise.dummyproject.platform.branding.dto;

public record PlatformBrandingResponse(

        Long id,

        String platformName,

        String logoUrl,

        String primaryColor,

        String secondaryColor,

        String faviconUrl
) {
}