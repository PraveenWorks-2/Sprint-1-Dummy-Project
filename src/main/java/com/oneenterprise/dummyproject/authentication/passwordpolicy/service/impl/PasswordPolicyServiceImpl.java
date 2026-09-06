package com.oneenterprise.dummyproject.authentication.passwordpolicy.service.impl;

import com.oneenterprise.dummyproject.authentication.passwordpolicy.service.PasswordPolicyService;

import org.springframework.stereotype.Service;

@Service
public class PasswordPolicyServiceImpl
        implements PasswordPolicyService {

    @Override
    public void validatePassword(String password) {

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException(
                    "Password must contain at least 8 characters");
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one uppercase letter");
        }

        if (!password.matches(".*[a-z].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one lowercase letter");
        }

        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one number");
        }

        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            throw new IllegalArgumentException(
                    "Password must contain at least one special character");
        }
    }
}