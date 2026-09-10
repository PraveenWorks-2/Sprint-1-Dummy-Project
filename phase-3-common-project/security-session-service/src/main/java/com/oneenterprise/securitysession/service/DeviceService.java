package com.oneenterprise.securitysession.service;

import com.oneenterprise.securitysession.dto.DeviceRequest;
import com.oneenterprise.securitysession.dto.DeviceResponse;

import java.util.List;

public interface DeviceService {

    DeviceResponse registerDevice(DeviceRequest request);

    DeviceResponse getDevice(Long id);

    List<DeviceResponse> getUserDevices(Long userId);

    void deactivateDevice(Long id);
}