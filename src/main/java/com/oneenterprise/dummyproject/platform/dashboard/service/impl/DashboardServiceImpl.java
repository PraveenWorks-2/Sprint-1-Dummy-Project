package com.oneenterprise.dummyproject.platform.dashboard.service.impl;

import com.oneenterprise.dummyproject.platform.configuration.repository.PlatformConfigRepository;
import com.oneenterprise.dummyproject.platform.dashboard.dto.DashboardMetricsResponseDto;
import com.oneenterprise.dummyproject.platform.dashboard.service.DashboardService;
import com.oneenterprise.dummyproject.platform.settings.repository.PlatformSettingsRepository;
import com.oneenterprise.dummyproject.platform.superadmin.repository.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final SuperAdminRepository superAdminRepository;
    private final PlatformConfigRepository configRepository;
    private final PlatformSettingsRepository settingsRepository;

    @Autowired
    public DashboardServiceImpl(SuperAdminRepository superAdminRepository,
                                PlatformConfigRepository configRepository,
                                PlatformSettingsRepository settingsRepository) {
        this.superAdminRepository = superAdminRepository;
        this.configRepository = configRepository;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public DashboardMetricsResponseDto getDashboardMetrics() {
        long totalAdmins = superAdminRepository.count();
        long activeAdmins = superAdminRepository.countByIsActiveTrue();
        long totalConfigs = configRepository.count();
        long totalSettings = settingsRepository.count();

        
        Runtime runtime = Runtime.getRuntime();
        long freeMemoryMB = runtime.freeMemory() / (1024 * 1024);
        long totalMemoryMB = runtime.totalMemory() / (1024 * 1024);
        long usedMemoryMB = totalMemoryMB - freeMemoryMB;
        long uptimeMinutes = ManagementFactory.getRuntimeMXBean().getUptime() / (1000 * 60);

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("usedMemoryMB", usedMemoryMB);
        metrics.put("totalMemoryMB", totalMemoryMB);
        metrics.put("availableProcessors", runtime.availableProcessors());
        metrics.put("uptimeMinutes", uptimeMinutes);

        return new DashboardMetricsResponseDto(
                totalAdmins,
                activeAdmins,
                totalConfigs,
                totalSettings,
                "HEALTHY_ONLINE",
                System.getProperty("java.version"),
                LocalDateTime.now(),
                metrics
        );
    }
}