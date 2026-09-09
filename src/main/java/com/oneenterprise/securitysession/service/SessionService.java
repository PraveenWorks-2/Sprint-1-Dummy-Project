package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.SessionRequest;
import com.oneenterprise.securitysession.dto.SessionResponse;
import com.oneenterprise.securitysession.dto.SessionValidationResponse;

import java.util.List;

public interface SessionService {

    SessionResponse createSession(SessionRequest request);

    SessionResponse getSession(Long id);

    List<SessionResponse> getUserSessions(Long userId);

    void terminateSession(Long id);
    SessionValidationResponse validateSession(String token);
    
}