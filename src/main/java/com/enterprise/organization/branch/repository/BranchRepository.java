package com.enterprise.organization.branch.repository;

import com.enterprise.organization.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {
    boolean existsByBranchCodeIgnoreCase(String branchCode);
    Optional<Branch> findByBranchCodeIgnoreCase(String branchCode);
    List<Branch> findByLocation_LocationId(Long locationId);
}
