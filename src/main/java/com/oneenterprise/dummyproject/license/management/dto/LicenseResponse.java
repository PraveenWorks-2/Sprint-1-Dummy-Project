package com.oneenterprise.dummyproject.license.management.dto;

import java.time.LocalDate;

public record LicenseResponse(

        Long id,

        String licenseKey,

        String licenseType,

        LocalDate startDate,

        LocalDate expiryDate,

        boolean active
) {
}