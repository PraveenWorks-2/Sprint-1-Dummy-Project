package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.dto.SecurityValidationResponse;
import com.oneenterprise.securitysession.repository.LoginHistoryRepository;
import com.oneenterprise.securitysession.repository.UserDeviceRepository;
import com.oneenterprise.securitysession.repository.UserSessionRepository;
import com.oneenterprise.securitysession.service.AccountLockoutService;
import com.oneenterprise.securitysession.service.SecurityService;

import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserSessionRepository sessionRepository;
    private final UserDeviceRepository deviceRepository;
    private final LoginHistoryRepository loginRepository;
    private final AccountLockoutService lockoutService;

    public SecurityServiceImpl(
            UserSessionRepository sessionRepository,
            UserDeviceRepository deviceRepository,
            LoginHistoryRepository loginRepository, 
            AccountLockoutService lockoutService) {

        this.sessionRepository = sessionRepository;
        this.deviceRepository = deviceRepository;
        this.loginRepository = loginRepository;
		this.lockoutService = lockoutService;
    }

    @Override
    public SecurityValidationResponse validateAccount(Long userId) {

        long activeSessions =
                sessionRepository.countByUserIdAndActiveTrue(userId);

        long activeDevices =
                deviceRepository.countByUserIdAndActiveTrue(userId);

        long failedLogins =
                loginRepository.countByUserIdAndSuccessFalse(userId);

        boolean accountLocked =
                lockoutService.isAccountLocked(userId);
        
        boolean mfaEnabled =
                lockoutService.isMfaEnabled(userId);
        
        int failedAttempts =
                lockoutService.getFailedAttempts(userId);
                
        boolean secure =
                activeDevices > 0 &&
                activeSessions > 0 &&
                failedLogins < 5 && 
                !accountLocked;

        String message;

        if (accountLocked) {

            message = "Account is locked";

        } else if (failedLogins >= 5) {

            message = "Account security validation requires attention due to failed login attempts";

        } else if (activeDevices == 0) {

            message = "No active devices found";

        } else if (activeSessions == 0) {

            message = "No active sessions found";

        } else {

            message = "Account security validation passed";
        }

        return SecurityValidationResponse.builder()
                .userId(userId)
                .accountSecure(secure)
                .activeSessions(activeSessions)
                .activeDevices(activeDevices)
                .failedLogins(failedLogins)
                .accountLocked(accountLocked)
                .failedAttempts(failedAttempts)
                .mfaEnabled(mfaEnabled)
                .message(message)
                .build();
    }
}