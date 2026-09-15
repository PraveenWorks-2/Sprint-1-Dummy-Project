package com.oneenterprise.dummyproject.platform.dashboard.controller;

import com.oneenterprise.dummyproject.platform.common.response.ApiResponse;
import com.oneenterprise.dummyproject.platform.dashboard.dto.DashboardMetricsResponseDto;
import com.oneenterprise.dummyproject.platform.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/platform/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/metrics")
    public ResponseEntity<ApiResponse<DashboardMetricsResponseDto>> getDashboardMetrics() {
        DashboardMetricsResponseDto metrics = dashboardService.getDashboardMetrics();
        return ResponseEntity.ok(ApiResponse.success("Dashboard metrics retrieved successfully", metrics));
    }
}