package com.oneenterprise.securitysession.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginHistoryResponse {

    private Long id;
    private Long userId;
    private String deviceId;
    private String ipAddress;
    private LocalDateTime loginTime;
    private boolean success;
    private String failureReason;
}