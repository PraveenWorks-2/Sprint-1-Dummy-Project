package com.oneenterprise.dummyproject.authentication.loginvalidation.exception;

public class AccountDisabledException extends RuntimeException {
    public AccountDisabledException(String message) {
        super(message);
    }
}