package com.oneenterprise.dummyproject.platform.configuration.service.impl;

import com.oneenterprise.dummyproject.platform.common.exception.ResourceNotFoundException;
import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigRequestDto;
import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigResponseDto;
import com.oneenterprise.dummyproject.platform.configuration.entity.PlatformConfig;
import com.oneenterprise.dummyproject.platform.configuration.repository.PlatformConfigRepository;
import com.oneenterprise.dummyproject.platform.configuration.service.PlatformConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlatformConfigServiceImpl implements PlatformConfigService {

    private final PlatformConfigRepository configRepository;

    @Autowired
    public PlatformConfigServiceImpl(PlatformConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public PlatformConfigResponseDto saveOrUpdateConfig(PlatformConfigRequestDto request) {
        Optional<PlatformConfig> existing = configRepository.findByConfigKey(request.getConfigKey());

        PlatformConfig config;
        if (existing.isPresent()) {
            config = existing.get();
            config.setConfigValue(request.getConfigValue());
            config.setDescription(request.getDescription());
            config.setIsEncrypted(request.getIsEncrypted());
        } else {
            config = new PlatformConfig(
                    request.getConfigKey(),
                    request.getConfigValue(),
                    request.getDescription(),
                    request.getIsEncrypted()
            );
        }

        PlatformConfig saved = configRepository.save(config);
        return mapToDto(saved);
    }

    @Override
    public List<PlatformConfigResponseDto> getAllConfigs() {
        return configRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PlatformConfigResponseDto getConfigByKey(String key) {
        PlatformConfig config = configRepository.findByConfigKey(key)
                .orElseThrow(() -> new ResourceNotFoundException("Configuration not found with key: " + key));
        return mapToDto(config);
    }

    @Override
    public void deleteConfig(Long id) {
        if (!configRepository.existsById(id)) {
            throw new ResourceNotFoundException("Configuration not found with id: " + id);
        }
        configRepository.deleteById(id);
    }

    private PlatformConfigResponseDto mapToDto(PlatformConfig config) {
        return new PlatformConfigResponseDto(
                config.getId(),
                config.getConfigKey(),
                config.getConfigValue(),
                config.getDescription(),
                config.getIsEncrypted(),
                config.getUpdatedAt()
        );
    }
}