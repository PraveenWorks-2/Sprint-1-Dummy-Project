package com.oneenterprise.dummyproject.platform.settings.service;

import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingRequestDto;
import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingResponseDto;

import java.util.List;

public interface PlatformSettingsService {
    PlatformSettingResponseDto saveOrUpdateSetting(PlatformSettingRequestDto request);
    List<PlatformSettingResponseDto> getAllSettings();
    List<PlatformSettingResponseDto> getSettingsByCategory(String category);
    PlatformSettingResponseDto getSettingByKey(String key);
}