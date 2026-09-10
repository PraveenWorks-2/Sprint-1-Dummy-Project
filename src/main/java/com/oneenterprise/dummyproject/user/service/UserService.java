package com.oneenterprise.dummyproject.user.service;

import com.oneenterprise.dummyproject.user.dto.UserProfileUpdateDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;

import java.util.UUID;

public interface UserService {

    UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto);

    UserRegistrationResponseDto getUserById(UUID id);

    UserRegistrationResponseDto updateProfile(UUID id, UserProfileUpdateDto requestDto);

    UserRegistrationResponseDto activateUser(UUID id);

    UserRegistrationResponseDto deactivateUser(UUID id);
}