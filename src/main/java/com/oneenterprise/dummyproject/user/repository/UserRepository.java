package com.oneenterprise.dummyproject.user.repository;

import com.oneenterprise.dummyproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
