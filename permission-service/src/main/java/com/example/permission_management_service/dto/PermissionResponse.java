package com.example.permission_management_service.dto;
import java.time.LocalDateTime;

import com.example.permission_management_service.entity.Permission_Entity;

public record PermissionResponse(
        Long id,
        String name,
        String code,
        String description,
        String category,
        String module,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PermissionResponse from(Permission_Entity p) {
        return new PermissionResponse(
                p.getId(), p.getName(), p.getCode(), p.getDescription(),
                p.getCategory(), p.getModule(), p.getActive(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}