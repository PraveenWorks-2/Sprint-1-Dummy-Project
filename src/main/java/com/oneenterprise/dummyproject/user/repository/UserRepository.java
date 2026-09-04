package com.oneenterprise.dummyproject.user.repository;

import com.oneenterprise.dummyproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
}