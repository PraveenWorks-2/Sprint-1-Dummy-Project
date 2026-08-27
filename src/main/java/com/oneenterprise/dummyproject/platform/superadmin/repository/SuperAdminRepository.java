package com.oneenterprise.dummyproject.platform.superadmin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneenterprise.dummyproject.platform.superadmin.entity.SuperAdmin;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long>{
  
	Optional<SuperAdmin> findByEmail(String email);
	boolean existsByEmail(String email);
	long countByIsActiveTrue();
}
