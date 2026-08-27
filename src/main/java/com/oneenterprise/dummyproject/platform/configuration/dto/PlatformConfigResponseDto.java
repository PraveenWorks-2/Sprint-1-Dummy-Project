package com.oneenterprise.dummyproject.platform.configuration.dto;

import java.time.LocalDateTime;

public class PlatformConfigResponseDto {
    private Long id;
    private String configKey;
    private String configValue;
    private String description;
    private Boolean isEncrypted;
    private LocalDateTime updatedAt;

    public PlatformConfigResponseDto() {}

    public PlatformConfigResponseDto(Long id, String configKey, String configValue, String description, Boolean isEncrypted, LocalDateTime updatedAt) {
        this.id = id;
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.isEncrypted = isEncrypted;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }

    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsEncrypted() { return isEncrypted; }
    public void setIsEncrypted(Boolean isEncrypted) { this.isEncrypted = isEncrypted; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}