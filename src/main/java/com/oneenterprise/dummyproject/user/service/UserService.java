package com.oneenterprise.dummyproject.user.service;

import com.oneenterprise.dummyproject.user.dto.UserRegistrationRequestDto;
import com.oneenterprise.dummyproject.user.dto.UserRegistrationResponseDto;
import com.oneenterprise.dummyproject.user.entity.User;

public interface UserService {

    UserRegistrationResponseDto registerUser(UserRegistrationRequestDto requestDto);

    UserRegistrationResponseDto getUserById(Long id);

    UserRegistrationResponseDto activateUser(Long id);

    UserRegistrationResponseDto deactivateUser(Long id);


}
