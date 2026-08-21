package com.oneenterprise.dummyproject.platform.configuration.controller;

import com.oneenterprise.dummyproject.platform.common.response.ApiResponse;
import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigRequestDto;
import com.oneenterprise.dummyproject.platform.configuration.dto.PlatformConfigResponseDto;
import com.oneenterprise.dummyproject.platform.configuration.service.PlatformConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/configurations")
public class PlatformConfigController {

    private final PlatformConfigService configService;

    @Autowired
    public PlatformConfigController(PlatformConfigService configService) {
        this.configService = configService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlatformConfigResponseDto>> saveOrUpdateConfig(@RequestBody PlatformConfigRequestDto request) {
        PlatformConfigResponseDto response = configService.saveOrUpdateConfig(request);
        return new ResponseEntity<>(ApiResponse.success("Configuration saved successfully", response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlatformConfigResponseDto>>> getAllConfigs() {
        List<PlatformConfigResponseDto> response = configService.getAllConfigs();
        return ResponseEntity.ok(ApiResponse.success("Configurations fetched successfully", response));
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<ApiResponse<PlatformConfigResponseDto>> getConfigByKey(@PathVariable String key) {
        PlatformConfigResponseDto response = configService.getConfigByKey(key);
        return ResponseEntity.ok(ApiResponse.success("Configuration fetched successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteConfig(@PathVariable Long id) {
        configService.deleteConfig(id);
        return ResponseEntity.ok(ApiResponse.success("Configuration deleted successfully", null));
    }
}