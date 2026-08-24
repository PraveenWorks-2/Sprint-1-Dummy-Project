package com.enterprise.organization.businessunit.dto;

import com.enterprise.organization.businessunit.entity.BusinessUnitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessUnitResponseDTO {
    private Long businessUnitId;
    private String businessUnitCode;
    private String businessUnitName;
    private String description;
    private Long companyId;
    private String companyName;
    private BusinessUnitStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
