package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessionResponse {

    private Long id;
    private Long userId;
    private String sessionToken;
    private String deviceId;
    private String ipAddress;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
}