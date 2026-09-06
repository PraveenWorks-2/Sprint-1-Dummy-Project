package com.enterprise.organization.location.dto;

import com.enterprise.organization.location.entity.LocationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {
    private Long locationId;
    private String locationCode;
    private String locationName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private LocationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
