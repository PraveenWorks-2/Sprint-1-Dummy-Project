package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.SecurityValidationResponse;
import com.oneenterprise.securitysession.repository.LoginHistoryRepository;
import com.oneenterprise.securitysession.repository.UserDeviceRepository;
import com.oneenterprise.securitysession.repository.UserSessionRepository;
import com.oneenterprise.securitysession.service.SecurityService;

import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserSessionRepository sessionRepository;
    private final UserDeviceRepository deviceRepository;
    private final LoginHistoryRepository loginRepository;

    public SecurityServiceImpl(
            UserSessionRepository sessionRepository,
            UserDeviceRepository deviceRepository,
            LoginHistoryRepository loginRepository) {

        this.sessionRepository = sessionRepository;
        this.deviceRepository = deviceRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public SecurityValidationResponse validateAccount(Long userId) {

        long activeSessions =
                sessionRepository.countByUserIdAndActiveTrue(userId);

        long activeDevices =
                deviceRepository.countByUserIdAndActiveTrue(userId);

        long failedLogins =
                loginRepository.countByUserIdAndSuccessFalse(userId);

        boolean secure =
                activeDevices > 0 &&
                activeSessions > 0 &&
                failedLogins < 5;

        String message;

        if (secure) {
            message = "Account security validation passed";
        } else {
            message = "Account security validation requires attention";
        }

        return SecurityValidationResponse.builder()
                .userId(userId)
                .accountSecure(secure)
                .activeSessions(activeSessions)
                .activeDevices(activeDevices)
                .failedLogins(failedLogins)
                .message(message)
                .build();
    }
}