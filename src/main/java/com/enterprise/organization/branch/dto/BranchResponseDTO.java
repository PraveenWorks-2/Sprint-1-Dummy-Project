package com.enterprise.organization.branch.dto;

import com.enterprise.organization.branch.entity.BranchStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponseDTO {
    private Long branchId;
    private String branchCode;
    private String branchName;
    private String description;
    private Long locationId;
    private String locationName;
    private BranchStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
