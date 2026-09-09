package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.SsoValidationRequest;
import com.oneenterprise.securitysession.dto.SsoValidationResponse;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.service.AccountLockoutService;
import com.oneenterprise.securitysession.service.SsoService;

import org.springframework.stereotype.Service;

@Service
public class SsoServiceImpl implements SsoService {

    private final AccountLockoutService lockoutService;
    private final SecurityEventProducer securityEventProducer;

    public SsoServiceImpl(
            AccountLockoutService lockoutService,
            SecurityEventProducer securityEventProducer) {

        this.lockoutService = lockoutService;
        this.securityEventProducer = securityEventProducer;
    }

    @Override
    public SsoValidationResponse validate(
            SsoValidationRequest request) {

        Long userId = request.getUserId();

        if (lockoutService.isAccountLocked(userId)) {

            return SsoValidationResponse.builder()
                    .userId(userId)
                    .provider(request.getProvider())
                    .externalSubject(request.getExternalSubject())
                    .valid(false)
                    .accountLocked(true)
                    .message("Account is locked")
                    .build();
        }

        if (!Boolean.TRUE.equals(
                request.getProviderValidated())) {

            lockoutService.recordFailedAttempt(userId);

            securityEventProducer.publish(
                    "SSO_VALIDATION_FAILED",
                    1L,
                    userId,
                    "SsoValidation",
                    request.getExternalSubject(),
                    "SSO validation failed for provider "
                            + request.getProvider()
            );

            return SsoValidationResponse.builder()
                    .userId(userId)
                    .provider(request.getProvider())
                    .externalSubject(request.getExternalSubject())
                    .valid(false)
                    .accountLocked(
                            lockoutService.isAccountLocked(userId)
                    )
                    .message("SSO validation failed")
                    .build();
        }

        lockoutService.recordSuccessfulAuthentication(
                userId
        );
        
        securityEventProducer.publish(
                "SSO_VALIDATION_SUCCESS",
                1L,
                userId,
                "SsoValidation",
                request.getExternalSubject(),
                "SSO validation successful for provider "
                        + request.getProvider()
        );

        return SsoValidationResponse.builder()
                .userId(userId)
                .provider(request.getProvider())
                .externalSubject(request.getExternalSubject())
                .valid(true)
                .accountLocked(false)
                .message("SSO validation successful")
                .build();
    }
}