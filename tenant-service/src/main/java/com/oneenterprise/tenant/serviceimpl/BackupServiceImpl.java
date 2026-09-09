package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.BackupResponse;
import com.oneenterprise.tenant.entity.*;
import com.oneenterprise.tenant.exception.ResourceNotFoundException;
import com.oneenterprise.tenant.repository.TenantBackupRepository;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import com.oneenterprise.tenant.service.BackupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service @Transactional
public class BackupServiceImpl implements BackupService {
    private final TenantRepository tenantRepository; private final TenantBackupRepository backupRepository; private final TenantConfigurationRepository configRepository; private final String location;
    public BackupServiceImpl(TenantRepository t, TenantBackupRepository b, TenantConfigurationRepository c, @Value("${tenant.backup.location:./backups}") String location){tenantRepository=t;backupRepository=b;configRepository=c;this.location=location;}
    @Override public BackupResponse requestBackup(UUID tenantId){
        if(!tenantRepository.existsById(tenantId)) throw new ResourceNotFoundException("Tenant with id '"+tenantId+"' not found");
        TenantConfiguration c=configRepository.findByTenantId(tenantId).orElseThrow(()->new ResourceNotFoundException("Configuration for tenant '"+tenantId+"' not found"));
        TenantBackup b=new TenantBackup(); b.setTenantId(tenantId); b.setStatus(BackupStatus.REQUESTED); b.setBackupType("POSTGRESQL_LOGICAL"); b.setLocation(location+"/tenant-"+tenantId+"/pending"); b.setRetentionUntil(OffsetDateTime.now().plusDays(c.getBackupRetentionDays()));
        // This service records a backup request/retention contract.
        // Actual pg_dump execution belongs to the deployment backup worker.
        return toResponse(backupRepository.save(b));
    }
    @Override @Transactional(readOnly=true) public List<BackupResponse> history(UUID tenantId){ if(!tenantRepository.existsById(tenantId)) throw new ResourceNotFoundException("Tenant with id '"+tenantId+"' not found"); return backupRepository.findByTenantIdOrderByCreatedAtDesc(tenantId).stream().map(this::toResponse).toList(); }
    private BackupResponse toResponse(TenantBackup b){BackupResponse r=new BackupResponse();r.setId(b.getId());r.setTenantId(b.getTenantId());r.setStatus(b.getStatus());r.setBackupType(b.getBackupType());r.setLocation(b.getLocation());r.setCreatedAt(b.getCreatedAt());r.setRetentionUntil(b.getRetentionUntil());return r;}
}
