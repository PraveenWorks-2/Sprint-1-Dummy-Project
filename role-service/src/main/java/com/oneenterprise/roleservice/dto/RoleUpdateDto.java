package com.oneenterprise.roleservice.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleUpdateDto {

    @Size(min = 2, max = 100, message = "Role name must be between 2 and 100 characters")
    private String roleName;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private Boolean isActive;
}