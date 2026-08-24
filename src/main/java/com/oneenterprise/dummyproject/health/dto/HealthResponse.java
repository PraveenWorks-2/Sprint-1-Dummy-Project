package com.oneenterprise.dummyproject.health.dto;

public record HealthResponse(

        String application,

        String status,

        String database

) {
}