package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.entity.TenantConfiguration;
import com.oneenterprise.tenant.repository.TenantBackupRepository;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BackupServiceImplTest {
    @Test void requestBackup_shouldCreateRetentionMetadata(){
        TenantRepository tenants=mock(TenantRepository.class); TenantBackupRepository backups=mock(TenantBackupRepository.class); TenantConfigurationRepository configs=mock(TenantConfigurationRepository.class);
        UUID id=UUID.randomUUID(); when(tenants.existsById(id)).thenReturn(true); TenantConfiguration c=new TenantConfiguration(); c.setTenantId(id); c.setBackupRetentionDays(90); when(configs.findByTenantId(id)).thenReturn(Optional.of(c)); when(backups.save(any())).thenAnswer(i->i.getArgument(0));
        var out=new BackupServiceImpl(tenants,backups,configs,"./backups").requestBackup(id);
        assertEquals("REQUESTED",out.getStatus().name()); assertEquals("POSTGRESQL_LOGICAL",out.getBackupType()); assertNotNull(out.getRetentionUntil()); verify(backups).save(any());
    }
}
