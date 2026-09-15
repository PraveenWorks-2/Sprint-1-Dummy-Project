package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.MfaChallengeRequest;
import com.oneenterprise.securitysession.dto.MfaChallengeResponse;
import com.oneenterprise.securitysession.dto.MfaValidationRequest;
import com.oneenterprise.securitysession.dto.MfaValidationResponse;
import com.oneenterprise.securitysession.service.MfaService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/security/mfa")
public class MfaController {

    private final MfaService service;

    public MfaController(MfaService service) {
        this.service = service;
    }

    @PostMapping("/challenge")
    public ResponseEntity<MfaChallengeResponse> createChallenge(
            @Valid @RequestBody MfaChallengeRequest request) {

        return ResponseEntity.ok(
                service.createChallenge(request)
        );
    }

    @PostMapping("/validate")
    public ResponseEntity<MfaValidationResponse> validateChallenge(
            @Valid @RequestBody MfaValidationRequest request) {

        return ResponseEntity.ok(
                service.validateChallenge(request)
        );
    }

    @PutMapping("/{userId}/enable")
    public ResponseEntity<Void> enableMfa(
            @PathVariable Long userId) {

        service.enableMfa(userId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/disable")
    public ResponseEntity<Void> disableMfa(
            @PathVariable Long userId) {

        service.disableMfa(userId);

        return ResponseEntity.noContent().build();
    }
}