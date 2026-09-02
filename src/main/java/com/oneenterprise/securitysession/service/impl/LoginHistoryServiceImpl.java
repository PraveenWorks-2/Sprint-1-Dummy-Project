package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.LoginHistoryRequest;
import com.oneenterprise.securitysession.dto.LoginHistoryResponse;
import com.oneenterprise.securitysession.entity.LoginHistory;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.repository.LoginHistoryRepository;
import com.oneenterprise.securitysession.service.LoginHistoryService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginHistoryServiceImpl implements LoginHistoryService {

    private final LoginHistoryRepository repository;
    private final SecurityEventProducer securityEventProducer;

    public LoginHistoryServiceImpl(
            LoginHistoryRepository repository, SecurityEventProducer securityEventProducer) {

        this.repository = repository;
		this.securityEventProducer = securityEventProducer;
    }

    @Override
    public LoginHistoryResponse recordLogin(
            LoginHistoryRequest request) {

        LoginHistory history = LoginHistory.builder()
                .userId(request.getUserId())
                .deviceId(request.getDeviceId())
                .ipAddress(request.getIpAddress())
                .loginTime(LocalDateTime.now())
                .success(request.getSuccess())
                .failureReason(request.getFailureReason())
                .build();

        LoginHistory saved = repository.save(history);

        String eventType = saved.isSuccess()
                ? "LOGIN_SUCCESS"
                : "LOGIN_FAILED";

        securityEventProducer.publish(
                eventType,
                saved.getUserId(),
                null,
                saved.getDeviceId(),
                saved.getIpAddress(),
                saved.isSuccess(),
                saved.isSuccess()
                        ? "Successful login recorded"
                        : "Failed login recorded"
        );

        return map(saved);
    }

    @Override
    public List<LoginHistoryResponse> getLoginHistory(
            Long userId) {

        return repository
                .findByUserIdOrderByLoginTimeDesc(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    private LoginHistoryResponse map(LoginHistory history) {

        return LoginHistoryResponse.builder()
                .id(history.getId())
                .userId(history.getUserId())
                .deviceId(history.getDeviceId())
                .ipAddress(history.getIpAddress())
                .loginTime(history.getLoginTime())
                .success(history.isSuccess())
                .failureReason(history.getFailureReason())
                .build();
    }
}