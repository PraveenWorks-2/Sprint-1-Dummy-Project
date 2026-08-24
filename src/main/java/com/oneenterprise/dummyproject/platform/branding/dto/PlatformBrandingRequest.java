package com.oneenterprise.dummyproject.platform.branding.dto;

import jakarta.validation.constraints.NotBlank;

public record PlatformBrandingRequest(

        @NotBlank(message = "Platform name is required")
        String platformName,

        String logoUrl,

        String primaryColor,

        String secondaryColor,

        String faviconUrl
) {
}