package com.oneenterprise.securitysession.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MfaRedisService {

    private static final String PREFIX = "mfa:challenge:";

    private final StringRedisTemplate redisTemplate;

    public MfaRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveChallenge(
            String challengeId,
            Long userId,
            String otp,
            Duration duration) {

        String key = PREFIX + challengeId;

        String value = userId + ":" + otp;

        redisTemplate.opsForValue().set(
                key,
                value,
                duration
        );
    }

    public String getChallenge(String challengeId) {

        return redisTemplate.opsForValue()
                .get(PREFIX + challengeId);
    }

    public void deleteChallenge(String challengeId) {

        redisTemplate.delete(
                PREFIX + challengeId
        );
    }

    public boolean challengeExists(String challengeId) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(
                        PREFIX + challengeId
                )
        );
    }
}