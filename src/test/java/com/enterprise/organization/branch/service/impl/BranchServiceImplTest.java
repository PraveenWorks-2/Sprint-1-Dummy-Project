package com.enterprise.organization.branch.service.impl;

import com.enterprise.organization.branch.dto.BranchRequestDTO;
import com.enterprise.organization.branch.entity.Branch;
import com.enterprise.organization.branch.repository.BranchRepository;
import com.enterprise.organization.branch.service.BranchMapper;
import com.enterprise.organization.location.entity.Location;
import com.enterprise.organization.location.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceImplTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private BranchMapper branchMapper;

    @InjectMocks
    private BranchServiceImpl branchService;

    @Test
    void createBranch_shouldValidateLocation() {
        BranchRequestDTO request = new BranchRequestDTO();
        request.setBranchCode("HYD-BR");
        request.setBranchName("Hyderabad Branch");
        request.setLocationId(10L);

        Location location = new Location();
        location.setLocationId(10L);

        Branch branch = new Branch();
        when(branchRepository.existsByBranchCodeIgnoreCase("HYD-BR")).thenReturn(false);
        when(locationRepository.findById(10L)).thenReturn(Optional.of(location));
        when(branchMapper.toEntity(request, location)).thenReturn(branch);
        when(branchRepository.save(branch)).thenReturn(branch);

        branchService.createBranch(request);

        verify(branchRepository).save(branch);
    }

    @Test
    void createBranch_shouldThrowWhenLocationMissing() {
        BranchRequestDTO request = new BranchRequestDTO();
        request.setBranchCode("HYD-BR");
        request.setBranchName("Hyderabad Branch");
        request.setLocationId(99L);

        when(branchRepository.existsByBranchCodeIgnoreCase("HYD-BR")).thenReturn(false);
        when(locationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> branchService.createBranch(request));
    }
}
