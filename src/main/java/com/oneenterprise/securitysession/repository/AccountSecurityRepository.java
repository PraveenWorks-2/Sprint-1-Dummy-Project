package com.oneenterprise.securitysession.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneenterprise.securitysession.entity.AccountSecurity;
import java.util.List;


@Repository
public interface AccountSecurityRepository extends JpaRepository<AccountSecurity, Long>{	

	Optional<AccountSecurity> findByUserId(Long userId);
	
}
