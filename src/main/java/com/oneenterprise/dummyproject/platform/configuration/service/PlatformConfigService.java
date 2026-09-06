package com.oneenterprise.dummyproject.platform.configuration.service;

import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigRequestDto;
import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigResponseDto;

import java.util.List;

public interface PlatformConfigService {
    PlatformConfigResponseDto saveOrUpdateConfig(PlatformConfigRequestDto request);
    List<PlatformConfigResponseDto> getAllConfigs();
    PlatformConfigResponseDto getConfigByKey(String key);
    void deleteConfig(Long id);
}