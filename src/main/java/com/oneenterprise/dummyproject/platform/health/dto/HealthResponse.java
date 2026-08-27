package com.oneenterprise.dummyproject.platform.health.dto;

public record HealthResponse(

        String application,

        String status,

        String database

) {
}