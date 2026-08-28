package com.oneenterprise.dummyproject.role_permission.client;

import com.oneenterprise.dummyproject.role_permission.dto.RoleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient (name = "role-service", url = "${role-service.url}")

public interface RoleClient {

    @GetMapping("/api/v1/roles/{id}")
    RoleResponseDto getRoleById(@PathVariable("id") Long id);
}
