package com.example.permission_management_service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.permission_management_service.entity.Permission_Entity;

public interface PermissionRepository extends JpaRepository<Permission_Entity, Long> {
    Optional<Permission_Entity> findByCode(String code);
    boolean existsByCode(String code);
    List<Permission_Entity> findByCategoryIgnoreCase(String category);
    List<Permission_Entity> findByModuleIgnoreCase(String module);
    List<Permission_Entity> findByActiveTrue();
}