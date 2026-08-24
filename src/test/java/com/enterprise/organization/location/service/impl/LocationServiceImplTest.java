package com.enterprise.organization.location.service.impl;

import com.enterprise.organization.location.dto.LocationRequestDTO;
import com.enterprise.organization.location.entity.Location;
import com.enterprise.organization.location.repository.LocationRepository;
import com.enterprise.organization.location.service.LocationMapper;
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
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    @Test
    void createLocation_shouldSaveLocation() {
        LocationRequestDTO request = new LocationRequestDTO();
        request.setLocationCode("HYD");
        request.setLocationName("Hyderabad Office");
        request.setAddressLine1("Hitech City");
        request.setCity("Hyderabad");
        request.setState("Telangana");
        request.setCountry("India");
        request.setPostalCode("500081");

        Location entity = new Location();
        entity.setLocationId(1L);

        when(locationRepository.existsByLocationCodeIgnoreCase("HYD")).thenReturn(false);
        when(locationMapper.toEntity(request)).thenReturn(entity);
        when(locationRepository.save(entity)).thenReturn(entity);

        var response = locationService.createLocation(request);

        verify(locationRepository).save(entity);
        verify(locationMapper).toResponseDTO(entity);
    }

    @Test
    void getLocationById_shouldThrowWhenMissing() {
        when(locationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> locationService.getLocationById(99L));
    }
}
