package com.enterprise.organization.branch.service;

import com.enterprise.organization.branch.dto.BranchRequestDTO;
import com.enterprise.organization.branch.dto.BranchResponseDTO;
import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.branch.entity.BranchStatus;
import com.enterprise.organization.location.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {

    public Branch toEntity(BranchRequestDTO dto, Location location) {
        return Branch.builder()
                .branchCode(dto.getBranchCode())
                .branchName(dto.getBranchName())
                .description(dto.getDescription())
                .location(location)
                .status(BranchStatus.ACTIVE)
                .build();
    }

    public void updateEntity(Branch branch, BranchRequestDTO dto, Location location) {
        branch.setBranchCode(dto.getBranchCode());
        branch.setBranchName(dto.getBranchName());
        branch.setDescription(dto.getDescription());
        branch.setLocation(location);
    }

    public BranchResponseDTO toResponseDTO(Branch branch) {
        return BranchResponseDTO.builder()
                .branchId(branch.getBranchId())
                .branchCode(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .description(branch.getDescription())
                .locationId(branch.getLocation().getLocationId())
                .locationName(branch.getLocation().getLocationName())
                .status(branch.getStatus())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }
}
