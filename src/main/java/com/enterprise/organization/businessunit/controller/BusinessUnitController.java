package com.enterprise.organization.businessunit.controller;

import com.enterprise.organization.businessunit.dto.BusinessUnitRequestDTO;
import com.enterprise.organization.businessunit.dto.BusinessUnitResponseDTO;
import com.enterprise.organization.businessunit.service.BusinessUnitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/business-units")
@RequiredArgsConstructor
@Tag(name = "Business Units", description = "Business unit management under a company (Naveen Kumar Vaddepalli)")
public class BusinessUnitController {

    private final BusinessUnitService businessUnitService;

    @PostMapping
    public ResponseEntity<BusinessUnitResponseDTO> createBusinessUnit(@Valid @RequestBody BusinessUnitRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(businessUnitService.createBusinessUnit(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<BusinessUnitResponseDTO>> getAllBusinessUnits() {
        return ResponseEntity.ok(businessUnitService.getAllBusinessUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessUnitResponseDTO> getBusinessUnitById(@PathVariable("id") Long businessUnitId) {
        return ResponseEntity.ok(businessUnitService.getBusinessUnitById(businessUnitId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<BusinessUnitResponseDTO>> getBusinessUnitsByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(businessUnitService.getBusinessUnitsByCompany(companyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessUnitResponseDTO> updateBusinessUnit(@PathVariable("id") Long businessUnitId,
                                                                        @Valid @RequestBody BusinessUnitRequestDTO requestDTO) {
        return ResponseEntity.ok(businessUnitService.updateBusinessUnit(businessUnitId, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BusinessUnitResponseDTO> updateStatus(@PathVariable("id") Long businessUnitId,
                                                                  @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(businessUnitService.updateBusinessUnitStatus(businessUnitId, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessUnit(@PathVariable("id") Long businessUnitId) {
        businessUnitService.deleteBusinessUnit(businessUnitId);
        return ResponseEntity.noContent().build();
    }
}
