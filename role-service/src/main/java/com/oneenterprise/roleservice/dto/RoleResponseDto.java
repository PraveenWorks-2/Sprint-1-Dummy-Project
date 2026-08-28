package com.oneenterprise.roleservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponseDto {
    private Long id;
    private String roleName;
    private String description;
    private String tenantId;
    private Boolean isCustom;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}