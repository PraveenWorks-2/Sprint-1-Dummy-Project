package com.enterprise.organization.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequestDTO {

    @NotBlank(message = "locationCode is required")
    @Size(max = 30, message = "locationCode must not exceed 30 characters")
    private String locationCode;

    @NotBlank(message = "locationName is required")
    @Size(max = 150, message = "locationName must not exceed 150 characters")
    private String locationName;

    @NotBlank(message = "addressLine1 is required")
    @Size(max = 255, message = "addressLine1 must not exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "addressLine2 must not exceed 255 characters")
    private String addressLine2;

    @NotBlank(message = "city is required")
    @Size(max = 100, message = "city must not exceed 100 characters")
    private String city;

    @NotBlank(message = "state is required")
    @Size(max = 100, message = "state must not exceed 100 characters")
    private String state;

    @NotBlank(message = "country is required")
    @Size(max = 100, message = "country must not exceed 100 characters")
    private String country;

    @NotBlank(message = "postalCode is required")
    @Size(max = 20, message = "postalCode must not exceed 20 characters")
    private String postalCode;
}
