package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.SessionRequest;
import com.oneenterprise.securitysession.dto.SessionResponse;
import com.oneenterprise.securitysession.entity.UserSession;
import com.oneenterprise.securitysession.exception.ResourceNotFoundException;
import com.oneenterprise.securitysession.redis.SessionRedisService;
import com.oneenterprise.securitysession.repository.UserSessionRepository;
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
    private final SessionRedisService redisService;

    public SessionServiceImpl(
            UserSessionRepository sessionRepository,
            SessionRedisService redisService) {

        this.sessionRepository = sessionRepository;
        this.redisService = redisService;
    }

    @Override
    @Transactional
    public SessionResponse createSession(SessionRequest request) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiry = now.plusHours(1);

        String token = UUID.randomUUID().toString();

        UserSession session = UserSession.builder()
                .userId(request.getUserId())
                .sessionToken(token)
                .deviceId(request.getDeviceId())
                .ipAddress(request.getIpAddress())
                .createdAt(now)
                .expiresAt(expiry)
                .active(true)
                .build();

        UserSession saved = sessionRepository.save(session);

        redisService.saveSession(
                token,
                request.getUserId(),
                request.getDeviceId(),
                Duration.ofHours(1)
        );

        return mapToResponse(saved);
    }

    @Override
    public SessionResponse getSession(Long id) {

        UserSession session = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Session not found with ID: " + id));

        return mapToResponse(session);
    }

    @Override
    public List<SessionResponse> getUserSessions(Long userId) {

        return sessionRepository
                .findByUserIdAndActiveTrue(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void terminateSession(Long id) {

        UserSession session = sessionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Session not found with ID: " + id));

        session.setActive(false);
        sessionRepository.save(session);

        redisService.deleteSession(
                session.getSessionToken()
        );
    }

    private SessionResponse mapToResponse(UserSession session) {

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