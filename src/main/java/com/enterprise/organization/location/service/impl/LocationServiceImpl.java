package com.enterprise.organization.location.service.impl;

import com.enterprise.organization.common.exception.DuplicateResourceException;
import com.enterprise.organization.common.exception.ResourceNotFoundException;
import com.enterprise.organization.location.dto.LocationRequestDTO;
import com.enterprise.organization.location.dto.LocationResponseDTO;
import com.enterprise.organization.location.entity.Location;
import com.enterprise.organization.location.entity.LocationStatus;
import com.enterprise.organization.location.repository.LocationRepository;
import com.enterprise.organization.location.service.LocationMapper;
import com.enterprise.organization.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public LocationResponseDTO createLocation(LocationRequestDTO requestDTO) {
        if (locationRepository.existsByLocationCodeIgnoreCase(requestDTO.getLocationCode())) {
            throw new DuplicateResourceException(
                    "Location already exists with locationCode: " + requestDTO.getLocationCode());
        }
        Location saved = locationRepository.save(locationMapper.toEntity(requestDTO));
        return locationMapper.toResponseDTO(saved);
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toResponseDTO)
                .toList();
    }

    @Override
    public LocationResponseDTO getLocationById(Long locationId) {
        return locationMapper.toResponseDTO(findLocationOrThrow(locationId));
    }

    @Override
    @Transactional
    public LocationResponseDTO updateLocation(Long locationId, LocationRequestDTO requestDTO) {
        Location existing = findLocationOrThrow(locationId);

        locationRepository.findByLocationCodeIgnoreCase(requestDTO.getLocationCode())
                .filter(other -> !other.getLocationId().equals(locationId))
                .ifPresent(other -> {
                    throw new DuplicateResourceException(
                            "Another location already uses locationCode: " + requestDTO.getLocationCode());
                });

        locationMapper.updateEntity(existing, requestDTO);
        return locationMapper.toResponseDTO(locationRepository.save(existing));
    }

    @Override
    @Transactional
    public LocationResponseDTO updateLocationStatus(Long locationId, String status) {
        Location existing = findLocationOrThrow(locationId);
        LocationStatus newStatus;
        try {
            newStatus = LocationStatus.valueOf(status.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentException("status must be one of ACTIVE, INACTIVE");
        }
        existing.setStatus(newStatus);
        return locationMapper.toResponseDTO(locationRepository.save(existing));
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        Location existing = findLocationOrThrow(locationId);
        locationRepository.delete(existing);
    }

    private Location findLocationOrThrow(Long locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + locationId));
    }
}
