package com.oneenterprise.dummyproject.user.dto;

import com.oneenterprise.dummyproject.user.enums.UserStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserRegistrationResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private UUID tenantId;

    private UUID departmentId;

    private UserStatus status;

    private LocalDate createdAt;

    private String createdBy;

    private LocalDate updatedAt;

    private String updatedBy;

    private boolean deleted;

    private LocalDate deletedAt;

    private String deletedBy;
}