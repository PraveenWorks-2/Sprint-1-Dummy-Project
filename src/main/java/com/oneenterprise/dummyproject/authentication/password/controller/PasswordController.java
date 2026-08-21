package com.oneenterprise.dummyproject.authentication.password.controller;

import com.oneenterprise.dummyproject.authentication.password.dto.ChangePasswordRequest;
import com.oneenterprise.dummyproject.authentication.password.service.PasswordService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping("/change")
    public ResponseEntity<String> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request) {

        passwordService.changePassword(authentication
        		.getName(),request);

        return ResponseEntity.ok("Password changed successfully");
    }
}