package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityValidationResponse {

    private Long userId;
    private boolean accountSecure;
    private long activeSessions;
    private long activeDevices;
    private long failedLogins;
    private String message;
}