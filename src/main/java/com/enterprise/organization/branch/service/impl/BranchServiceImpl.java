package com.enterprise.organization.branch.service.impl;

import com.enterprise.organization.branch.dto.BranchRequestDTO;
import com.enterprise.organization.branch.dto.BranchResponseDTO;
import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.branch.entity.BranchStatus;
import com.enterprise.organization.branch.repository.BranchRepository;
import com.enterprise.organization.branch.service.BranchMapper;
import com.enterprise.organization.branch.service.BranchService;
import com.enterprise.organization.common.exception.DuplicateResourceException;
import com.enterprise.organization.common.exception.ResourceNotFoundException;
import com.enterprise.organization.location.entity.Location;
import com.enterprise.organization.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final LocationRepository locationRepository;
    private final BranchMapper branchMapper;

    @Override
    @Transactional
    public BranchResponseDTO createBranch(BranchRequestDTO requestDTO) {
        if (branchRepository.existsByBranchCodeIgnoreCase(requestDTO.getBranchCode())) {
            throw new DuplicateResourceException(
                    "Branch already exists with branchCode: " + requestDTO.getBranchCode());
        }
        Location location = findLocationOrThrow(requestDTO.getLocationId());
        Branch saved = branchRepository.save(branchMapper.toEntity(requestDTO, location));
        return branchMapper.toResponseDTO(saved);
    }

    @Override
    public List<BranchResponseDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branchMapper::toResponseDTO)
                .toList();
    }

    @Override
    public BranchResponseDTO getBranchById(Long branchId) {
        return branchMapper.toResponseDTO(findBranchOrThrow(branchId));
    }

    @Override
    public List<BranchResponseDTO> getBranchesByLocation(Long locationId) {
        findLocationOrThrow(locationId);
        return branchRepository.findByLocation_LocationId(locationId).stream()
                .map(branchMapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public BranchResponseDTO updateBranch(Long branchId, BranchRequestDTO requestDTO) {
        Branch existing = findBranchOrThrow(branchId);

        branchRepository.findByBranchCodeIgnoreCase(requestDTO.getBranchCode())
                .filter(other -> !other.getBranchId().equals(branchId))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Another branch already uses branchCode: " + requestDTO.getBranchCode());
                });

        Location location = findLocationOrThrow(requestDTO.getLocationId());
        branchMapper.updateEntity(existing, requestDTO, location);
        return branchMapper.toResponseDTO(branchRepository.save(existing));
    }

    @Override
    @Transactional
    public BranchResponseDTO updateBranchStatus(Long branchId, String status) {
        Branch existing = findBranchOrThrow(branchId);
        BranchStatus newStatus;
        try {
            newStatus = BranchStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentException("status must be one of ACTIVE, INACTIVE");
        }
        existing.setStatus(newStatus);
        return branchMapper.toResponseDTO(branchRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteBranch(Long branchId) {
        Branch existing = findBranchOrThrow(branchId);
        branchRepository.delete(existing);
    }

    private Branch findBranchOrThrow(Long branchId) {
        return branchRepository.findById(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found with id: " + branchId));
    }

    private Location findLocationOrThrow(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + locationId));
    }
}
