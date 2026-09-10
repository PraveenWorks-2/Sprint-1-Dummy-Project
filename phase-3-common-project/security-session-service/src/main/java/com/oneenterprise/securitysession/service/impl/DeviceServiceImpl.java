package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.DeviceRequest;
import com.oneenterprise.securitysession.dto.DeviceResponse;
import com.oneenterprise.securitysession.entity.UserDevice;
import com.oneenterprise.securitysession.exception.ResourceNotFoundException;
import com.oneenterprise.securitysession.repository.UserDeviceRepository;
import com.oneenterprise.securitysession.service.DeviceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final UserDeviceRepository repository;

    public DeviceServiceImpl(UserDeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public DeviceResponse registerDevice(DeviceRequest request) {

        UserDevice device = repository
                .findByUserIdAndDeviceId(
                        request.getUserId(),
                        request.getDeviceId()
                )
                .orElse(
                        UserDevice.builder()
                                .userId(request.getUserId())
                                .deviceId(request.getDeviceId())
                                .createdAt(LocalDateTime.now())
                                .build()
                );

        device.setDeviceName(request.getDeviceName());
        device.setIpAddress(request.getIpAddress());
        device.setLastUsedAt(LocalDateTime.now());
        device.setActive(true);

        return map(repository.save(device));
    }

    @Override
    public DeviceResponse getDevice(Long id) {

        UserDevice device = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Device not found with ID: " + id));

        return map(device);
    }

    @Override
    public List<DeviceResponse> getUserDevices(Long userId) {

        return repository
                .findByUserIdAndActiveTrue(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    @Transactional
    public void deactivateDevice(Long id) {

        UserDevice device = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Device not found with ID: " + id));

        device.setActive(false);
        repository.save(device);
    }

    private DeviceResponse map(UserDevice device) {

        return DeviceResponse.builder()
                .id(device.getId())
                .userId(device.getUserId())
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .ipAddress(device.getIpAddress())
                .createdAt(device.getCreatedAt())
                .lastUsedAt(device.getLastUsedAt())
                .active(device.isActive())
                .build();
    }
}