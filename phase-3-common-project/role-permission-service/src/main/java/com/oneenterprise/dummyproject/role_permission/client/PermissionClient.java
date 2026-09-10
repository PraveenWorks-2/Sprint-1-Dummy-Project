package com.oneenterprise.dummyproject.role_permission.client;

import com.oneenterprise.dummyproject.role_permission.dto.PermissionResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "permission-service", url = "${permission-service.url}")

public interface PermissionClient {

    @GetMapping("/api/permissions/{id}")
    PermissionResponseDto getPermissionById(@PathVariable("id") Long id);
}
