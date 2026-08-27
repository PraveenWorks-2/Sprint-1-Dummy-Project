package com.oneenterprise.dummyproject.platform.settings.dto;

import java.time.LocalDateTime;

public class PlatformSettingResponseDto {
    private Long id;
    private String settingKey;
    private String settingValue;
    private String category;
    private String description;
    private LocalDateTime updatedAt;

    public PlatformSettingResponseDto() {}

    public PlatformSettingResponseDto(Long id, String settingKey, String settingValue, String category, String description, LocalDateTime updatedAt) {
        this.id = id;
        this.settingKey = settingKey;
        this.settingValue = settingValue;
        this.category = category;
        this.description = description;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSettingKey() { return settingKey; }
    public void setSettingKey(String settingKey) { this.settingKey = settingKey; }

    public String getSettingValue() { return settingValue; }
    public void setSettingValue(String settingValue) { this.settingValue = settingValue; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}