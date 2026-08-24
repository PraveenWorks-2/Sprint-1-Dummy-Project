package com.enterprise.organization.branch.service;

import com.enterprise.organization.branch.dto.BranchRequestDTO;
import com.enterprise.organization.branch.dto.BranchResponseDTO;

import java.util.List;

public interface BranchService {
    BranchResponseDTO createBranch(BranchRequestDTO requestDTO);
    List<BranchResponseDTO> getAllBranches();
    BranchResponseDTO getBranchById(Long branchId);
    List<BranchResponseDTO> getBranchesByLocation(Long locationId);
    BranchResponseDTO updateBranch(Long branchId, BranchRequestDTO requestDTO);
    BranchResponseDTO updateBranchStatus(Long branchId, String status);
    void deleteBranch(Long branchId);
}
