package com.oneenterprise.securitysession.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class SessionRedisService {
	
	private static final String PREFIX = "session:";

    private final StringRedisTemplate redisTemplate;

    public SessionRedisService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(
            String token,
            Long userId,
            String deviceId,
            Duration duration) {

        String key = PREFIX + token;
        String value = userId + ":" + deviceId;

        redisTemplate.opsForValue().set(
                key,
                value,
                duration
        );
    }

    public String getSession(String token) {

        return redisTemplate
                .opsForValue()
                .get(PREFIX + token);
    }

    public void deleteSession(String token) {

        redisTemplate.delete(PREFIX + token);
    }

    public boolean sessionExists(String token) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(PREFIX + token)
        );
    }
    
    public Long getSessionTtlSeconds(String token) {
    	return redisTemplate.getExpire(PREFIX + token);
    }
}