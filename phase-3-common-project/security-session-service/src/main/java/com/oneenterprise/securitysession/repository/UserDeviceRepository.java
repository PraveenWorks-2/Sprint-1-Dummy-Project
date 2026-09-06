package com.oneenterprise.securitysession.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneenterprise.securitysession.entity.UserDevice;

@Repository
public interface UserDeviceRepository extends JpaRepository<UserDevice, Long>{

	Optional<UserDevice> findByUserIdAndDeviceId(Long userId, String deviceId);
	
	List<UserDevice> findByUserIdAndActiveTrue(Long userId);
	long countByUserIdAndActiveTrue(Long userId);
	
}
