package com.enterprise.organization.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequestDTO {

    @NotBlank(message = "companyCode is required")
    @Size(max = 30, message = "companyCode must not exceed 30 characters")
    private String companyCode;

    @NotBlank(message = "companyName is required")
    @Size(max = 150, message = "companyName must not exceed 150 characters")
    private String companyName;

    @Size(max = 100, message = "industry must not exceed 100 characters")
    private String industry;

    @Size(max = 255, message = "address must not exceed 255 characters")
    private String address;

    @Email(message = "contactEmail must be a valid email address")
    @Size(max = 120)
    private String contactEmail;

    @Pattern(regexp = "^[0-9+\\-() ]{7,20}$", message = "contactPhone must be a valid phone number")
    private String contactPhone;
}
