package com.oneenterprise.securitysession.controller;

import com.oneenterprise.securitysession.dto.SessionRequest;
import com.oneenterprise.securitysession.dto.SessionResponse;
import com.oneenterprise.securitysession.service.SessionService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService service;

    public SessionController(SessionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SessionResponse> createSession(
            @Valid @RequestBody SessionRequest request) {

        return ResponseEntity.ok(
                service.createSession(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSession(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getSession(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SessionResponse>> getUserSessions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                service.getUserSessions(userId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> terminateSession(
            @PathVariable Long id) {

        service.terminateSession(id);

        return ResponseEntity.noContent().build();
    }
}