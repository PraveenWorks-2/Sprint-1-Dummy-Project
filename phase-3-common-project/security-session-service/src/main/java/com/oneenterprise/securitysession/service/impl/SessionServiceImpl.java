package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.SessionRequest;
import com.oneenterprise.securitysession.dto.SessionResponse;
import com.oneenterprise.securitysession.dto.SessionValidationResponse;
import com.oneenterprise.securitysession.entity.UserDevice;
import com.oneenterprise.securitysession.entity.UserSession;
import com.oneenterprise.securitysession.exception.ResourceNotFoundException;
import com.oneenterprise.securitysession.exception.SecurityValidationException;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.redis.SessionRedisService;
import com.oneenterprise.securitysession.repository.UserDeviceRepository;
import com.oneenterprise.securitysession.repository.UserSessionRepository;
import com.oneenterprise.securitysession.service.AccountLockoutService;
import com.oneenterprise.securitysession.service.SessionService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final UserSessionRepository sessionRepository;
    private final UserDeviceRepository deviceRepository;
    private final SessionRedisService redisService;
    private final SecurityEventProducer securityEventProducer;
    private final AccountLockoutService lockoutService;

    public SessionServiceImpl(
            UserSessionRepository sessionRepository,
            UserDeviceRepository deviceRepository,
            SessionRedisService redisService,
            SecurityEventProducer securityEventProducer,
            AccountLockoutService lockoutService) {

        this.sessionRepository = sessionRepository;
        this.deviceRepository = deviceRepository;
        this.redisService = redisService;
        this.securityEventProducer = securityEventProducer;
        this.lockoutService = lockoutService;
    }

    @Override
    @Transactional
    public SessionResponse createSession(
            SessionRequest request) {

        if (lockoutService.isAccountLocked(
                request.getUserId())) {

            throw new SecurityValidationException(
                    "Account is locked"
            );
        }

        UserDevice device =
                deviceRepository
                        .findByUserIdAndDeviceId(
                                request.getUserId(),
                                request.getDeviceId()
                        )
                        .orElseThrow(() ->
                                new SecurityValidationException(
                                        "Device is not registered"
                                )
                        );

        if (!device.isActive()) {
            throw new SecurityValidationException(
                    "Device is inactive"
            );
        }

        LocalDateTime now =
                LocalDateTime.now();

        LocalDateTime expiry =
                now.plusHours(1);

        String token =
                UUID.randomUUID().toString();

        UserSession session =
                UserSession.builder()
                        .userId(request.getUserId())
                        .sessionToken(token)
                        .deviceId(request.getDeviceId())
                        .ipAddress(request.getIpAddress())
                        .createdAt(now)
                        .expiresAt(expiry)
                        .active(true)
                        .build();

        UserSession saved =
                sessionRepository.save(session);

        redisService.saveSession(
                token,
                request.getUserId(),
                request.getDeviceId(),
                Duration.ofHours(1)
        );

        device.setLastUsedAt(now);
        deviceRepository.save(device);

        securityEventProducer.publish(
                "LOGIN",
                1L,
                request.getUserId(),
                "UserSession",
                saved.getId().toString(),
                "User session created for device "
                        + request.getDeviceId()
        );

        return mapToResponse(saved);
    }

    @Override
    public SessionResponse getSession(Long id) {

        UserSession session =
                sessionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Session not found with ID: "
                                                + id
                                )
                        );

        return mapToResponse(session);
    }

    @Override
    @Transactional
    public List<SessionResponse> getUserSessions(
            Long userId) {

        LocalDateTime now =
                LocalDateTime.now();

        List<UserSession> sessions =
                sessionRepository
                        .findByUserIdAndActiveTrue(userId);

        for (UserSession session : sessions) {

            if (session.getExpiresAt()
                    .isBefore(now)) {

                session.setActive(false);

                redisService.deleteSession(
                        session.getSessionToken()
                );

                securityEventProducer.publish(
                        "SESSION_EXPIRED",
                        1L,
                        session.getUserId(),
                        "UserSession",
                        session.getId().toString(),
                        "Session expired"
                );
            }
        }

        sessionRepository.saveAll(sessions);

        return sessions.stream()
                .filter(UserSession::isActive)
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void terminateSession(Long id) {

        UserSession session =
                sessionRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Session not found with ID: "
                                                + id
                        )
                        );

        session.setActive(false);

        sessionRepository.save(session);

        redisService.deleteSession(
                session.getSessionToken()
        );

        securityEventProducer.publish(
                "LOGOUT",
                1L,
                session.getUserId(),
                "UserSession",
                session.getId().toString(),
                "User session terminated"
        );
    }

    @Override
    @Transactional
    public SessionValidationResponse validateSession(
            String token) {

        UserSession session =
                sessionRepository
                        .findBySessionToken(token)
                        .orElse(null);

        boolean redisPresent =
                redisService.sessionExists(token);

        if (session == null) {

            securityEventProducer.publish(
                    "SESSION_INVALID",
                    1L,
                    null,
                    "UserSession",
                    token,
                    "Session token does not exist in database"
            );

            return SessionValidationResponse.builder()
                    .valid(false)
                    .redissSessionPresent(redisPresent)
                    .databaseSessionActive(false)
                    .deviceActive(false)
                    .expired(false)
                    .message("Session not found")
                    .build();
        }

        boolean expired =
                session.getExpiresAt()
                        .isBefore(LocalDateTime.now());

        UserDevice device =
                deviceRepository
                        .findByUserIdAndDeviceId(
                                session.getUserId(),
                                session.getDeviceId()
                        )
                        .orElse(null);

        boolean deviceActive =
                device != null &&
                        device.isActive();

        if (expired) {

            session.setActive(false);

            sessionRepository.save(session);

            redisService.deleteSession(token);

            securityEventProducer.publish(
                    "SESSION_EXPIRED",
                    1L,
                    session.getUserId(),
                    "UserSession",
                    session.getId().toString(),
                    "Session expired"
            );

            return buildValidationResponse(
                    session,
                    false,
                    false,
                    deviceActive,
                    true,
                    "Session has expired"
            );
        }

        if (!redisPresent) {

            session.setActive(false);

            sessionRepository.save(session);

            securityEventProducer.publish(
                    "SESSION_INVALID",
                    1L,
                    session.getUserId(),
                    "UserSession",
                    session.getId().toString(),
                    "Redis session is missing"
            );

            return buildValidationResponse(
                    session,
                    false,
                    false,
                    deviceActive,
                    false,
                    "Redis session not found"
            );
        }

        if (!session.isActive()) {

            redisService.deleteSession(token);

            return buildValidationResponse(
                    session,
                    false,
                    false,
                    deviceActive,
                    false,
                    "Database session is inactive"
            );
        }

        if (!deviceActive) {

            session.setActive(false);

            sessionRepository.save(session);

            redisService.deleteSession(token);

            securityEventProducer.publish(
                    "SESSION_INVALID",
                    1L,
                    session.getUserId(),
                    "UserSession",
                    session.getId().toString(),
                    "Session invalid because device is inactive"
            );

            return buildValidationResponse(
                    session,
                    false,
                    true,
                    false,
                    false,
                    "Associated device is inactive"
            );
        }

        return buildValidationResponse(
                session,
                true,
                true,
                true,
                false,
                "Session is valid"
        );
    }

    private SessionValidationResponse buildValidationResponse(
            UserSession session,
            boolean valid,
            boolean redisPresent,
            boolean deviceActive,
            boolean expired,
            String message) {

        return SessionValidationResponse.builder()
                .valid(valid)
                .sessionId(session.getId())
                .userId(session.getUserId())
                .deviceId(session.getDeviceId())
                .redissSessionPresent(redisPresent)
                .databaseSessionActive(session.isActive())
                .deviceActive(deviceActive)
                .expired(expired)
                .message(message)
                .build();
    }

    private SessionResponse mapToResponse(
            UserSession session) {

        return SessionResponse.builder()
                .id(session.getId())
                .userId(session.getUserId())
                .sessionToken(session.getSessionToken())
                .deviceId(session.getDeviceId())
                .ipAddress(session.getIpAddress())
                .createdAt(session.getCreatedAt())
                .expiresAt(session.getExpiresAt())
                .active(session.isActive())
                .build();
    }
}