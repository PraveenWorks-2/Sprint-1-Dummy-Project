package com.oneenterprise.dummyproject.platform.featuremanagement.dto;

public record PlatformFeatureResponse(
		
		Long id,

        String featureName,

        String description,

        boolean enabled
		
		) {

}
