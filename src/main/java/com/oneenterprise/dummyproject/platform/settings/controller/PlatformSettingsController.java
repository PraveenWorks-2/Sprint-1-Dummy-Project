package com.oneenterprise.dummyproject.platform.settings.controller;

import com.oneenterprise.dummyproject.platform.common.response.ApiResponse;
import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingRequestDto;
import com.oneenterprise.dummyproject.platform.settings.dto.PlatformSettingResponseDto;
import com.oneenterprise.dummyproject.platform.settings.service.PlatformSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platform/settings")
public class PlatformSettingsController {

    private final PlatformSettingsService settingsService;

    @Autowired
    public PlatformSettingsController(PlatformSettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlatformSettingResponseDto>> saveOrUpdateSetting(@RequestBody PlatformSettingRequestDto request) {
        PlatformSettingResponseDto response = settingsService.saveOrUpdateSetting(request);
        return new ResponseEntity<>(ApiResponse.success("Setting saved successfully", response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlatformSettingResponseDto>>> getAllSettings() {
        List<PlatformSettingResponseDto> response = settingsService.getAllSettings();
        return ResponseEntity.ok(ApiResponse.success("Settings fetched successfully", response));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<PlatformSettingResponseDto>>> getSettingsByCategory(@PathVariable String category) {
        List<PlatformSettingResponseDto> response = settingsService.getSettingsByCategory(category);
        return ResponseEntity.ok(ApiResponse.success("Category settings fetched successfully", response));
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<ApiResponse<PlatformSettingResponseDto>> getSettingByKey(@PathVariable String key) {
        PlatformSettingResponseDto response = settingsService.getSettingByKey(key);
        return ResponseEntity.ok(ApiResponse.success("Setting fetched successfully", response));
    }
}