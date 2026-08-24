package com.enterprise.organization.location.repository;

import com.enterprise.organization.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    boolean existsByLocationCodeIgnoreCase(String locationCode);
    Optional<Location> findByLocationCodeIgnoreCase(String locationCode);
}
