package com.oneenterprise.dummyproject.platform.settings.repository;

import com.oneenterprise.dummyproject.platform.settings.entity.PlatformSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformSettingsRepository extends JpaRepository<PlatformSetting, Long> {
    Optional<PlatformSetting> findBySettingKey(String settingKey);
    List<PlatformSetting> findByCategory(String category);
}