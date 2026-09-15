package com.oneenterprise.dummyproject.platform.configuration.dto;

public class PlatformConfigRequestDto {
    private String configKey;
    private String configValue;
    private String description;
    private Boolean isEncrypted;

    public PlatformConfigRequestDto() {}

    public PlatformConfigRequestDto(String configKey, String configValue, String description, Boolean isEncrypted) {
        this.configKey = configKey;
        this.configValue = configValue;
        this.description = description;
        this.isEncrypted = isEncrypted;
    }

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }

    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getIsEncrypted() { return isEncrypted; }
    public void setIsEncrypted(Boolean isEncrypted) { this.isEncrypted = isEncrypted; }
}