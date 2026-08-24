package com.oneenterprise.dummyproject.license.management.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record LicenseRequest(

        @NotBlank(message = "License key is required")
        String licenseKey,

        @NotBlank(message = "License type is required")
        String licenseType,

        LocalDate startDate,

        LocalDate expiryDate,

        boolean active
) {
}
