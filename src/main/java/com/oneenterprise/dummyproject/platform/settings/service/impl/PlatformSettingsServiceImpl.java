package com.oneenterprise.dummyproject.platform.settings.service.impl;

import com.oneenterprise.dummyproject.platform.common.exception.ResourceNotFoundException;
import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingRequestDto;
import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingResponseDto;
import com.oneenterprise.dummyproject.platform.settings.entity.PlatformSetting;
import com.oneenterprise.dummyproject.platform.settings.repository.PlatformSettingsRepository;
import com.oneenterprise.dummyproject.platform.settings.service.PlatformSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformSettingsServiceImpl implements PlatformSettingsService {

    private final PlatformSettingsRepository settingsRepository;

    @Autowired
    public PlatformSettingsServiceImpl(PlatformSettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    @Override
    public PlatformSettingResponseDto saveOrUpdateSetting(PlatformSettingRequestDto request) {
        Optional<PlatformSetting> existing = settingsRepository.findBySettingKey(request.getSettingKey());

        PlatformSetting setting;
        if (existing.isPresent()) {
            setting = existing.get();
            setting.setSettingValue(request.getSettingValue());
            setting.setCategory(request.getCategory());
            setting.setDescription(request.getDescription());
        } else {
            setting = new PlatformSetting(
                    request.getSettingKey(),
                    request.getSettingValue(),
                    request.getCategory(),
                    request.getDescription()
            );
        }

        PlatformSetting saved = settingsRepository.save(setting);
        return mapToDto(saved);
    }

    @Override
    public List<PlatformSettingResponseDto> getAllSettings() {
        return settingsRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatformSettingResponseDto> getSettingsByCategory(String category) {
        return settingsRepository.findByCategory(category)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlatformSettingResponseDto getSettingByKey(String key) {
        PlatformSetting setting = settingsRepository.findBySettingKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Setting not found with key: " + key));
        return mapToDto(setting);
    }

    private PlatformSettingResponseDto mapToDto(PlatformSetting setting) {
        return new PlatformSettingResponseDto(
                setting.getId(),
                setting.getSettingKey(),
                setting.getSettingValue(),
                setting.getCategory(),
                setting.getDescription(),
                setting.getUpdatedAt()
        );
    }
}