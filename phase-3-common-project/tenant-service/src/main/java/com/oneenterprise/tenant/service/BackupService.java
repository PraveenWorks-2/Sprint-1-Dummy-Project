package com.oneenterprise.tenant.service;
import com.oneenterprise.tenant.dto.BackupResponse;
import java.util.List;
import java.util.UUID;
public interface BackupService { BackupResponse requestBackup(UUID tenantId); List<BackupResponse> history(UUID tenantId); }
