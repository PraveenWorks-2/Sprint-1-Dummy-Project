package com.oneenterprise.securitysession.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneenterprise.securitysession.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long>{
	
	List<UserSession> findByUserIdAndActiveTrue(Long userId);
	
	long countByUserIdAndActiveTrue(Long userId);

    Optional<UserSession> findBySessionToken(
            String sessionToken
    );

    List<UserSession> findByUserIdAndDeviceIdAndActiveTrue(
            Long userId,
            String deviceId
    );

    List<UserSession> findByDeviceIdAndActiveTrue(
            String deviceId
    );
}
