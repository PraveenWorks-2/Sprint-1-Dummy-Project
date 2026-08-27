package com.oneenterprise.dummyproject.authentication.mfa.controller;

import com.oneenterprise.dummyproject.authentication.mfa.dto.MfaResponse;
import com.oneenterprise.dummyproject.authentication.mfa.dto.MfaVerifyRequest;
import com.oneenterprise.dummyproject.authentication.mfa.service.MfaService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/mfa")
@RequiredArgsConstructor
public class MfaController {

    private final MfaService mfaService;

    @PostMapping("/generate")
    public ResponseEntity<MfaResponse> generateOtp(
            Authentication authentication) {

        String username = authentication.getName();
        mfaService.generateOtp(username);
        return ResponseEntity.ok(
                new MfaResponse( true,
                        "MFA OTP generated. Check application console."));
    }

    @PostMapping("/verify")
    public ResponseEntity<MfaResponse> verifyOtp(
            Authentication authentication,
            @Valid @RequestBody MfaVerifyRequest request) {

        String username = authentication.getName();

        boolean valid = mfaService.verifyOtp( username,
                        request.getOtp());

        if (!valid) {
            return ResponseEntity.badRequest().body(
                            new MfaResponse(
                                    false,
                                    "Invalid or expired OTP"));
        }

        return ResponseEntity.ok( new MfaResponse(
        		true, "MFA verification successful"));
    }
}