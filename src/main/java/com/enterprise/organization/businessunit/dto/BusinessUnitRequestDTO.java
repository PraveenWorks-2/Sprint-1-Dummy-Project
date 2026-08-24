package com.enterprise.organization.businessunit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessUnitRequestDTO {

    @NotBlank(message = "businessUnitCode is required")
    @Size(max = 30, message = "businessUnitCode must not exceed 30 characters")
    private String businessUnitCode;

    @NotBlank(message = "businessUnitName is required")
    @Size(max = 150, message = "businessUnitName must not exceed 150 characters")
    private String businessUnitName;

    @Size(max = 255, message = "description must not exceed 255 characters")
    private String description;

    @NotNull(message = "companyId is required")
    private Long companyId;
}
