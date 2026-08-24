package com.enterprise.organization.location.controller;

import com.enterprise.organization.location.dto.LocationRequestDTO;
import com.enterprise.organization.location.dto.LocationResponseDTO;
import com.enterprise.organization.location.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Location management (Rayi Mohan)")
public class LocationController {

    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponseDTO> createLocation(@Valid @RequestBody LocationRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createLocation(requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<LocationResponseDTO>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> getLocationById(@PathVariable("id") Long locationId) {
        return ResponseEntity.ok(locationService.getLocationById(locationId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponseDTO> updateLocation(
            @PathVariable("id") Long locationId,
            @Valid @RequestBody LocationRequestDTO requestDTO) {
        return ResponseEntity.ok(locationService.updateLocation(locationId, requestDTO));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<LocationResponseDTO> updateStatus(
            @PathVariable("id") Long locationId,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(locationService.updateLocationStatus(locationId, body.get("status")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
