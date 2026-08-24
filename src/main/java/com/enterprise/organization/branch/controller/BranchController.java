package com.enterprise.organization.branch.controller;

import com.enterprise.organization.branch.dto.BranchRequestDTO;
import com.enterprise.organization.branch.dto.BranchResponseDTO;
import com.enterprise.organization.branch.service.BranchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "Branch management (Rayi Mohan)")
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchResponseDTO> createBranch(@Valid @RequestBody BranchRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.createBranch(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable("id") Long branchId) {
        return ResponseEntity.ok(branchService.getBranchById(branchId));
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<BranchResponseDTO>> getBranchesByLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(branchService.getBranchesByLocation(locationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable("id") Long branchId,
            @Valid @RequestBody BranchRequestDTO requestDTO) {
        return ResponseEntity.ok(branchService.updateBranch(branchId, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BranchResponseDTO> updateStatus(
            @PathVariable("id") Long branchId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(branchService.updateBranchStatus(branchId, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable("id") Long branchId) {
        branchService.deleteBranch(branchId);
        return ResponseEntity.noContent().build();
    }
}
