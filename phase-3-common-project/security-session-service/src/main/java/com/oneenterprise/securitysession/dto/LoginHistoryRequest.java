package com.oneenterprise.securitysession.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginHistoryRequest {

    @NotNull
    private Long userId;

    private String deviceId;

    private String ipAddress;

    @NotNull
    private Boolean success;

    private String failureReason;
}