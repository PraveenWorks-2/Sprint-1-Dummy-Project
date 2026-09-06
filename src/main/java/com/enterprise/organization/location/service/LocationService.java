package com.enterprise.organization.location.service;

import com.enterprise.organization.location.dto.LocationRequestDTO;
import com.enterprise.organization.location.dto.LocationResponseDTO;

import java.util.List;

public interface LocationService {
    LocationResponseDTO createLocation(LocationRequestDTO requestDTO);
    List<LocationResponseDTO> getAllLocations();
    LocationResponseDTO getLocationById(Long locationId);
    LocationResponseDTO updateLocation(Long locationId, LocationRequestDTO requestDTO);
    LocationResponseDTO updateLocationStatus(Long locationId, String status);
    void deleteLocation(Long locationId);
}
