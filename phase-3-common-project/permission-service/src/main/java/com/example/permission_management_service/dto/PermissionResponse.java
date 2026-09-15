package com.example.permission_management_service.dto;
import java.time.LocalDateTime;

import com.example.permission_management_service.entity.PermissionType;
import com.example.permission_management_service.entity.Permission_Entity;

public record PermissionResponse(

        Long id,
        String name,
        String code,
        String description,
        String category,
        String module,
        PermissionType permissionType,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static PermissionResponse from(Permission_Entity permission) {

        return new PermissionResponse(

                permission.getId(),
                permission.getName(),
                permission.getCode(),
                permission.getDescription(),
                permission.getCategory(),
                permission.getModule(),
                permission.getPermissionType(),
                permission.getActive(),
                permission.getCreatedAt(),
                permission.getUpdatedAt()
        );
    }
}