package com.oneenterprise.securitysession.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneenterprise.securitysession.entity.LoginHistory;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

	List<LoginHistory>findByUserIdOrderByLoginTimeDesc(Long userId);
	long countByUserIdAndSuccessFalse(Long userId);
}
