package com.enterprise.organization.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchRequestDTO {

    @NotBlank(message = "branchCode is required")
    @Size(max = 30, message = "branchCode must not exceed 30 characters")
    private String branchCode;

    @NotBlank(message = "branchName is required")
    @Size(max = 150, message = "branchName must not exceed 150 characters")
    private String branchName;

    @Size(max = 255, message = "description must not exceed 255 characters")
    private String description;

    @NotNull(message = "locationId is required")
    private Long locationId;
}
