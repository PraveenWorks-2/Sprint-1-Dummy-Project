package com.oneenterprise.dummyproject.platform.license.dto;

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