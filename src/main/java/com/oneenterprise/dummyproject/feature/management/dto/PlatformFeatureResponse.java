package com.oneenterprise.dummyproject.feature.management.dto;

public record PlatformFeatureResponse(
		
		Long id,

        String featureName,

        String description,

        boolean enabled
		
		) {

}
