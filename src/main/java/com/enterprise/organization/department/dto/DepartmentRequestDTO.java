package com.enterprise.organization.department.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentRequestDTO {

    @NotBlank(message = "departmentCode is required")
    @Size(max = 30, message = "departmentCode must not exceed 30 characters")
    private String departmentCode;

    @NotBlank(message = "departmentName is required")
    @Size(max = 150, message = "departmentName must not exceed 150 characters")
    private String departmentName;

    @Size(max = 255, message = "description must not exceed 255 characters")
    private String description;

    @NotNull(message = "businessUnitId is required")
    private Long businessUnitId;

    @NotNull(message = "branchId is required")
    private Long branchId;
}
