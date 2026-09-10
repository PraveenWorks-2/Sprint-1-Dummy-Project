package com.oneenterprise.securitysession.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeviceRequest {

    @NotNull
    private Long userId;

    @NotBlank
    private String deviceId;

    @NotBlank
    private String deviceName;

    private String ipAddress;
}