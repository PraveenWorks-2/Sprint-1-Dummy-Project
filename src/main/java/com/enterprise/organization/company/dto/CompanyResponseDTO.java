package com.enterprise.organization.company.dto;

import com.enterprise.organization.company.entity.CompanyStatus;
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
public class CompanyResponseDTO {
    private Long companyId;
    private String companyCode;
    private String companyName;
    private String industry;
    private String address;
    private String contactEmail;
    private String contactPhone;
    private CompanyStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
