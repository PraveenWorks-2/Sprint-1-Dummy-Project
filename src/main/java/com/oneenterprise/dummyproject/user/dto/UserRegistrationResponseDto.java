package com.oneenterprise.dummyproject.user.dto;

import com.oneenterprise.dummyproject.user.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data

public class UserRegistrationResponseDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private UserStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserRegistrationResponseDto(Long id, String firstName, String lastName,
                                       String email, String phone, UserStatus status,
                                       LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
