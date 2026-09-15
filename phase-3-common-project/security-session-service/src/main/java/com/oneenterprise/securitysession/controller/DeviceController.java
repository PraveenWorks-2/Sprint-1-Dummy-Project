package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.DeviceRequest;
import com.oneenterprise.securitysession.dto.DeviceResponse;
import com.oneenterprise.securitysession.service.DeviceService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService service;

    public DeviceController(DeviceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> registerDevice(
            @Valid @RequestBody DeviceRequest request) {

        return ResponseEntity.ok(
                service.registerDevice(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDevice(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getDevice(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceResponse>> getUserDevices(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                service.getUserDevices(userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateDevice(
            @PathVariable Long id) {

        service.deactivateDevice(id);

        return ResponseEntity.noContent().build();
    }
}