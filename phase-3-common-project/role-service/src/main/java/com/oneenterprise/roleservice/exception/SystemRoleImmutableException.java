package com.oneenterprise.roleservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SystemRoleImmutableException extends RuntimeException {
    public SystemRoleImmutableException(String message) {
        super(message);
    }
}