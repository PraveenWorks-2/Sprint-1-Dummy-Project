package com.enterprise.organization.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI organizationManagementOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Organization Management API")
                .description("Sprint 1 Dummy Project - Team 2. Company Setup and Business Units by Naveen Kumar Vaddepalli; Departments, Branches and Locations by Rayi Mohan.")
                .version("v1.0.0"));
    }
}
