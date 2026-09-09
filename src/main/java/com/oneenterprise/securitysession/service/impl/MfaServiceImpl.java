package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.MfaChallengeRequest;
import com.oneenterprise.securitysession.dto.MfaChallengeResponse;
import com.oneenterprise.securitysession.dto.MfaValidationRequest;
import com.oneenterprise.securitysession.dto.MfaValidationResponse;
import com.oneenterprise.securitysession.exception.SecurityValidationException;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.redis.MfaRedisService;
import com.oneenterprise.securitysession.service.AccountLockoutService;
import com.oneenterprise.securitysession.service.MfaService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.UUID;

@Service
public class MfaServiceImpl implements MfaService {

    private final MfaRedisService redisService;
    private final AccountLockoutService lockoutService;
    private final SecurityEventProducer securityEventProducer;

    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.security.mfa.ttl-seconds:300}")
    private long ttlSeconds;

    @Value("${app.security.mfa.expose-otp:true}")
    private boolean exposeOtp;

    public MfaServiceImpl(
            MfaRedisService redisService,
            AccountLockoutService lockoutService,
            SecurityEventProducer securityEventProducer) {

        this.redisService = redisService;
        this.lockoutService = lockoutService;
        this.securityEventProducer = securityEventProducer;
    }

    @Override
    public MfaChallengeResponse createChallenge(
            MfaChallengeRequest request) {

        Long userId = request.getUserId();

        if (lockoutService.isAccountLocked(userId)) {
            throw new SecurityValidationException(
                    "Account is locked"
            );
        }

        String challengeId =
                UUID.randomUUID().toString();

        String otp =
                String.format(
                        "%06d",
                        secureRandom.nextInt(1_000_000)
                );

        redisService.saveChallenge(
                challengeId,
                userId,
                otp,
                Duration.ofSeconds(ttlSeconds)
        );

        return MfaChallengeResponse.builder()
                .userId(userId)
                .challengeId(challengeId)
                .expiresInSeconds(ttlSeconds)
                .otp(exposeOtp ? otp : null)
                .build();
    }

    @Override
    public MfaValidationResponse validateChallenge(
            MfaValidationRequest request) {

        Long userId = request.getUserId();

        if (lockoutService.isAccountLocked(userId)) {

            return MfaValidationResponse.builder()
                    .userId(userId)
                    .validated(false)
                    .accountLocked(true)
                    .message("Account is locked")
                    .build();
        }

        String stored =
                redisService.getChallenge(
                        request.getChallengeId()
                );

        if (stored == null) {

            securityEventProducer.publish(
                    "MFA_FAILED",
                    1L,
                    userId,
                    "MfaChallenge",
                    request.getChallengeId(),
                    "MFA challenge expired or not found"
            );

            lockoutService.recordFailedAttempt(userId);

            return MfaValidationResponse.builder()
                    .userId(userId)
                    .validated(false)
                    .accountLocked(
                            lockoutService.isAccountLocked(userId)
                    )
                    .message("MFA challenge expired or not found")
                    .build();
        }

        String[] values = stored.split(":", 2);

        if (values.length != 2) {

            throw new SecurityValidationException(
                    "Invalid MFA challenge data"
            );
        }

        Long storedUserId =
                Long.valueOf(values[0]);

        String storedOtp = values[1];

        if (!storedUserId.equals(userId)) {

            securityEventProducer.publish(
                    "MFA_FAILED",
                    1L,
                    userId,
                    "MfaChallenge",
                    request.getChallengeId(),
                    "MFA challenge does not belong to user"
            );

            lockoutService.recordFailedAttempt(userId);

            return MfaValidationResponse.builder()
                    .userId(userId)
                    .validated(false)
                    .accountLocked(
                            lockoutService.isAccountLocked(userId)
                    )
                    .message("Invalid MFA challenge")
                    .build();
        }

        if (!storedOtp.equals(request.getOtp())) {

            securityEventProducer.publish(
                    "MFA_FAILED",
                    1L,
                    userId,
                    "MfaChallenge",
                    request.getChallengeId(),
                    "Invalid MFA OTP"
            );

            lockoutService.recordFailedAttempt(userId);

            return MfaValidationResponse.builder()
                    .userId(userId)
                    .validated(false)
                    .accountLocked(
                            lockoutService.isAccountLocked(userId)
                    )
                    .message("Invalid MFA OTP")
                    .build();
        }

        redisService.deleteChallenge(
                request.getChallengeId()
        );

        lockoutService.recordSuccessfulAuthentication(userId);

        securityEventProducer.publish(
                "MFA_SUCCESS",
                1L,
                userId,
                "MfaChallenge",
                request.getChallengeId(),
                "MFA validation successful"
        );

        return MfaValidationResponse.builder()
                .userId(userId)
                .validated(true)
                .accountLocked(false)
                .message("MFA validation successful")
                .build();
    }

    @Override
    public void enableMfa(Long userId) {

        lockoutService.setMfaEnabled(
                userId,
                true
        );
    }

    @Override
    public void disableMfa(Long userId) {

        lockoutService.setMfaEnabled(
                userId,
                false
        );
    }
}