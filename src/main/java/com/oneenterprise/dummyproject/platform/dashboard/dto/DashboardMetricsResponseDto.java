package com.oneenterprise.dummyproject.platform.dashboard.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class DashboardMetricsResponseDto {
    private long totalSuperAdmins;
    private long activeSuperAdmins;
    private long totalConfigurations;
    private long totalSettings;
    private String platformStatus;
    private String javaVersion;
    private LocalDateTime generatedAt;
    private Map<String, Object> systemMetrics;

    public DashboardMetricsResponseDto() {}

    public DashboardMetricsResponseDto(long totalSuperAdmins, long activeSuperAdmins, long totalConfigurations, 
                                       long totalSettings, String platformStatus, String javaVersion, 
                                       LocalDateTime generatedAt, Map<String, Object> systemMetrics) {
        this.totalSuperAdmins = totalSuperAdmins;
        this.activeSuperAdmins = activeSuperAdmins;
        this.totalConfigurations = totalConfigurations;
        this.totalSettings = totalSettings;
        this.platformStatus = platformStatus;
        this.javaVersion = javaVersion;
        this.generatedAt = generatedAt;
        this.systemMetrics = systemMetrics;
    }

    public long getTotalSuperAdmins() { return totalSuperAdmins; }
    public void setTotalSuperAdmins(long totalSuperAdmins) { this.totalSuperAdmins = totalSuperAdmins; }

    public long getActiveSuperAdmins() { return activeSuperAdmins; }
    public void setActiveSuperAdmins(long activeSuperAdmins) { this.activeSuperAdmins = activeSuperAdmins; }

    public long getTotalConfigurations() { return totalConfigurations; }
    public void setTotalConfigurations(long totalConfigurations) { this.totalConfigurations = totalConfigurations; }

    public long getTotalSettings() { return totalSettings; }
    public void setTotalSettings(long totalSettings) { this.totalSettings = totalSettings; }

    public String getPlatformStatus() { return platformStatus; }
    public void setPlatformStatus(String platformStatus) { this.platformStatus = platformStatus; }

    public String getJavaVersion() { return javaVersion; }
    public void setJavaVersion(String javaVersion) { this.javaVersion = javaVersion; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public Map<String, Object> getSystemMetrics() { return systemMetrics; }
    public void setSystemMetrics(Map<String, Object> systemMetrics) { this.systemMetrics = systemMetrics; }
}