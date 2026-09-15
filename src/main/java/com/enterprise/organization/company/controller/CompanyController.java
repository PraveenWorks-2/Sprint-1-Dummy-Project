package com.enterprise.organization.company.controller;

import com.enterprise.organization.company.dto.CompanyRequestDTO;
import com.enterprise.organization.company.dto.CompanyResponseDTO;
import com.enterprise.organization.company.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Tag(name = "Company Setup", description = "Company registration and master data (Naveen Kumar Vaddepalli)")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> createCompany(@Valid @RequestBody CompanyRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(@PathVariable("id") Long companyId) {
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> updateCompany(@PathVariable("id") Long companyId,
                                                              @Valid @RequestBody CompanyRequestDTO requestDTO) {
        return ResponseEntity.ok(companyService.updateCompany(companyId, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CompanyResponseDTO> updateStatus(@PathVariable("id") Long companyId,
                                                             @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(companyService.updateCompanyStatus(companyId, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long companyId) {
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}
