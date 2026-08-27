package com.oneenterprise.dummyproject.authentication.login.controller;

import com.oneenterprise.dummyproject.authentication.login.dto.request.LoginRequest;
import com.oneenterprise.dummyproject.authentication.login.dto.response.LoginResponse;
import com.oneenterprise.dummyproject.authentication.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}