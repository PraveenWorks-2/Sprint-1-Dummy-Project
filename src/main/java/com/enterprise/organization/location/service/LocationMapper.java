package com.enterprise.organization.location.service;

import com.enterprise.organization.location.dto.LocationRequestDTO;
import com.enterprise.organization.location.dto.LocationResponseDTO;
import com.enterprise.organization.location.entity.Location;
import com.enterprise.organization.location.entity.LocationStatus;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationRequestDTO dto) {
        return Location.builder()
                .locationCode(dto.getLocationCode())
                .locationName(dto.getLocationName())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .postalCode(dto.getPostalCode())
                .status(LocationStatus.ACTIVE)
                .build();
    }

    public void updateEntity(Location location, LocationRequestDTO dto) {
        location.setLocationCode(dto.getLocationCode());
        location.setLocationName(dto.getLocationName());
        location.setAddressLine1(dto.getAddressLine1());
        location.setAddressLine2(dto.getAddressLine2());
        location.setCity(dto.getCity());
        location.setState(dto.getState());
        location.setCountry(dto.getCountry());
        location.setPostalCode(dto.getPostalCode());
    }

    public LocationResponseDTO toResponseDTO(Location location) {
        return LocationResponseDTO.builder()
                .locationId(location.getLocationId())
                .locationCode(location.getLocationCode())
                .locationName(location.getLocationName())
                .addressLine1(location.getAddressLine1())
                .addressLine2(location.getAddressLine2())
                .city(location.getCity())
                .state(location.getState())
                .country(location.getCountry())
                .postalCode(location.getPostalCode())
                .status(location.getStatus())
                .createdAt(location.getCreatedAt())
                .updatedAt(location.getUpdatedAt())
                .build();
    }
}
