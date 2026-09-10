package com.oneenterprise.userrole.redis;

import com.oneenterprise.userrole.dto.UserRoleResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserRoleCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "user:roles:";

    private static final long CACHE_DURATION = 30;

    public void saveUserRoles(
            Long userId,
            List<UserRoleResponse> roles) {

        String key = KEY_PREFIX + userId;

        redisTemplate.opsForValue().set(
                key,
                roles,
                CACHE_DURATION,
                TimeUnit.MINUTES
        );
    }

    @SuppressWarnings("unchecked")
    public List<UserRoleResponse> getUserRoles(Long userId) {

        String key = KEY_PREFIX + userId;

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return (List<UserRoleResponse>) value;
    }

    public void deleteUserRoles(Long userId) {

        String key = KEY_PREFIX + userId;

        redisTemplate.delete(key);
    }
}