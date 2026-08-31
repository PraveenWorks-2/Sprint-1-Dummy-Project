package com.oneenterprise.dummyproject.role_permission.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PermissionResponseDto {

    private Long id;

    private String name;

    private String code;

    private String description;

    private String category;

    private String module;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
