package com.oneenterprise.dummyproject.authentication.loginvalidation.exception;

public class AccountLockedException extends RuntimeException {
    public AccountLockedException(String message) {
        super(message);
    }
}