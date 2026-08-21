package com.oneenterprise.dummyproject.authentication.password.service.impl;

import com.oneenterprise.dummyproject.authentication.login.entity.User;
import com.oneenterprise.dummyproject.authentication.login.repository.UserRepository;
import com.oneenterprise.dummyproject.authentication.password.dto.ChangePasswordRequest;
import com.oneenterprise.dummyproject.authentication.password.service.PasswordService;
import com.oneenterprise.dummyproject.authentication.passwordpolicy.service.PasswordPolicyService;

import lombok.RequiredArgsConstructor;

import com.oneenterprise.dummyproject.authentication.loginvalidation.exception.InvalidCredentialsException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicyService passwordPolicyService;

    @Override
    @Transactional
    public void changePassword(
            String username,ChangePasswordRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new InvalidCredentialsException( "User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Current password is incorrect");
        }

        passwordPolicyService.validatePassword(request.getNewPassword());

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "New password must be different from current password");
        }

        String encryptedPassword = passwordEncoder.encode(
                        request.getNewPassword());

        user.setPassword(encryptedPassword);

        userRepository.save(user);
    }
}