package com.oneenterprise.securitysession.service.impl;

import com.oneenterprise.securitysession.entity.AccountSecurity;
import com.oneenterprise.securitysession.kafka.SecurityEventProducer;
import com.oneenterprise.securitysession.repository.AccountSecurityRepository;
import com.oneenterprise.securitysession.service.AccountLockoutService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountLockoutServiceImpl implements AccountLockoutService {

    private final AccountSecurityRepository repository;
    private final SecurityEventProducer securityEventProducer;

    @Value("${app.security.lockout.max-failed-attempts:5}")
    private int maxFailedAttempts;

    @Value("${app.security.lockout.duration-minutes:15}")
    private int lockoutDurationMinutes;

    public AccountLockoutServiceImpl(
            AccountSecurityRepository repository,
            SecurityEventProducer securityEventProducer) {

        this.repository = repository;
        this.securityEventProducer = securityEventProducer;
    }

    @Override
    @Transactional
    public boolean isAccountLocked(Long userId) {

        AccountSecurity accountSecurity =
                getOrCreate(userId);

        
        if (!accountSecurity.isLocked()) {
            return false;
        }
        
        if (accountSecurity.getLockedUntil() != null
                && LocalDateTime.now()
                        .isAfter(accountSecurity.getLockedUntil())) {

            accountSecurity.setLocked(false);
            accountSecurity.setLockedUntil(null);
            accountSecurity.setFailedAttempts(0);
            accountSecurity.setUpdatedAt(LocalDateTime.now());

            repository.save(accountSecurity);

            securityEventProducer.publish(
                    "ACCOUNT_UNLOCKED",
                    1L,
                    userId,
                    "AccountSecurity",
                    accountSecurity.getId().toString(),
                    "Account automatically unlocked after lockout period"
            );

            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public void recordFailedAttempt(Long userId) {

        AccountSecurity accountSecurity =
                getOrCreate(userId);

        
        int failedAttempts =
                accountSecurity.getFailedAttempts() + 1;

        accountSecurity.setFailedAttempts(failedAttempts);
        accountSecurity.setUpdatedAt(LocalDateTime.now());

        
        if (failedAttempts >= maxFailedAttempts) {

            accountSecurity.setLocked(true);

            accountSecurity.setLockedUntil(
                    LocalDateTime.now()
                            .plusMinutes(lockoutDurationMinutes)
            );

            securityEventProducer.publish(
                    "ACCOUNT_LOCKED",
                    1L,
                    userId,
                    "AccountSecurity",
                    accountSecurity.getId().toString(),
                    "Account locked after "
                            + failedAttempts
                            + " failed authentication attempts"
            );
        }

        repository.save(accountSecurity);
    }

    @Override
    @Transactional
    public void recordSuccessfulAuthentication(Long userId) {

        AccountSecurity accountSecurity =
                getOrCreate(userId);

        
        accountSecurity.setFailedAttempts(0);
        accountSecurity.setLocked(false);
        accountSecurity.setLockedUntil(null);
        accountSecurity.setUpdatedAt(LocalDateTime.now());

        repository.save(accountSecurity);
    }

    @Override
    @Transactional
    public void unlockAccount(Long userId) {

        AccountSecurity accountSecurity =
                getOrCreate(userId);

        accountSecurity.setFailedAttempts(0);
        accountSecurity.setLocked(false);
        accountSecurity.setLockedUntil(null);
        accountSecurity.setUpdatedAt(LocalDateTime.now());

        repository.save(accountSecurity);

        securityEventProducer.publish(
                "ACCOUNT_UNLOCKED",
                1L,
                userId,
                "AccountSecurity",
                accountSecurity.getId().toString(),
                "Account manually unlocked"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isMfaEnabled(Long userId) {

        AccountSecurity accountSecurity =
                repository.findByUserId(userId)
                        .orElse(null);

        if (accountSecurity == null) {
            return false;
        }

        return accountSecurity.isMfaEnabled();
    }

    @Override
    @Transactional
    public void setMfaEnabled(Long userId, boolean enabled) {

        AccountSecurity accountSecurity =
                getOrCreate(userId);

        accountSecurity.setMfaEnabled(enabled);
        accountSecurity.setUpdatedAt(LocalDateTime.now());

        repository.save(accountSecurity);
    }

    @Override
    @Transactional(readOnly = true)
    public int getFailedAttempts(Long userId) {

        AccountSecurity accountSecurity =
                repository.findByUserId(userId)
                        .orElse(null);

        if (accountSecurity == null) {
            return 0;
        }

        return accountSecurity.getFailedAttempts();
    }

    
    private AccountSecurity getOrCreate(Long userId) {

        return repository.findByUserId(userId)
                .orElseGet(() -> {

                    LocalDateTime now =
                            LocalDateTime.now();

                    AccountSecurity accountSecurity =
                            AccountSecurity.builder()
                                    .userId(userId)
                                    .failedAttempts(0)
                                    .locked(false)
                                    .lockedUntil(null)
                                    .mfaEnabled(false)
                                    .createdAt(now)
                                    .updatedAt(now)
                                    .build();

                    return repository.save(accountSecurity);
                });
    }
}