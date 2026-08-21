package com.oneenterprise.dummyproject.authentication.loginvalidation.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}