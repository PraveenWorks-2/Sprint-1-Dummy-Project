package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.SecurityValidationResponse;
import com.oneenterprise.securitysession.service.SecurityService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final SecurityService service;

    public SecurityController(SecurityService service) {
        this.service = service;
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<SecurityValidationResponse> validateAccount(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                service.validateAccount(userId)
        );
    }
}