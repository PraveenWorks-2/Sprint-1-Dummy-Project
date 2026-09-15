package com.enterprise.organization.department.controller;

import com.enterprise.organization.department.dto.DepartmentRequestDTO;
import com.enterprise.organization.department.dto.DepartmentResponseDTO;
import com.enterprise.organization.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Department management (Rayi Mohan)")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(
            @PathVariable("id") Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId));
    }

    @GetMapping("/business-unit/{businessUnitId}")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentsByBusinessUnit(
            @PathVariable Long businessUnitId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByBusinessUnit(businessUnitId));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<DepartmentResponseDTO>> getDepartmentsByBranch(
            @PathVariable Long branchId) {
        return ResponseEntity.ok(departmentService.getDepartmentsByBranch(branchId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(
            @PathVariable("id") Long departmentId,
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<DepartmentResponseDTO> updateStatus(
            @PathVariable("id") Long departmentId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                departmentService.updateDepartmentStatus(departmentId, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable("id") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }
}
