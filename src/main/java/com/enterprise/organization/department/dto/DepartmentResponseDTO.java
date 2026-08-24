package com.enterprise.organization.department.dto;

import com.enterprise.organization.department.entity.DepartmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponseDTO {
    private Long departmentId;
    private String departmentCode;
    private String departmentName;
    private String description;
    private Long businessUnitId;
    private String businessUnitName;
    private Long branchId;
    private String branchName;
    private DepartmentStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
