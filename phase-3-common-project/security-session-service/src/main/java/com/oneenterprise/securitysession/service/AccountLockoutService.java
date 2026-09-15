package com.oneenterprise.securitysession.service;

public interface AccountLockoutService {

	boolean isAccountLocked(Long userId);

    void recordFailedAttempt(Long userId);

    void recordSuccessfulAuthentication(Long userId);

    void unlockAccount(Long userId);

    boolean isMfaEnabled(Long userId);

    void setMfaEnabled(Long userId, boolean enabled);
    int getFailedAttempts(Long userId);
}
