package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.LoginHistoryRequest;
import com.oneenterprise.securitysession.dto.LoginHistoryResponse;
import com.oneenterprise.securitysession.service.LoginHistoryService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/login-history")
public class LoginHistoryController {

    private final LoginHistoryService service;

    public LoginHistoryController(LoginHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LoginHistoryResponse> recordLogin(
            @Valid @RequestBody LoginHistoryRequest request) {

        return ResponseEntity.ok(
                service.recordLogin(request)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoginHistoryResponse>> getHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                service.getLoginHistory(userId)
        );
    }
}