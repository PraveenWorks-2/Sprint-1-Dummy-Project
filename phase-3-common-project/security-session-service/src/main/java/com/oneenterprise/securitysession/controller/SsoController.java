package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.SsoValidationRequest;
import com.oneenterprise.securitysession.dto.SsoValidationResponse;
import com.oneenterprise.securitysession.service.SsoService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security/sso")
public class SsoController {

    private final SsoService service;

    public SsoController(SsoService service) {
        this.service = service;
    }

    @PostMapping("/validate")
    public ResponseEntity<SsoValidationResponse> validate(
            @Valid @RequestBody SsoValidationRequest request) {

        return ResponseEntity.ok(
                service.validate(request)
        );
    }
}