package com.oneenterprise.dummyproject.platform.featuremanagement.dto;

import jakarta.validation.constraints.NotBlank;

public record PlatformFeatureRequest(

        @NotBlank(message = "Feature name is required")
        String featureName,

        String description,

        boolean enabled
) {
}
