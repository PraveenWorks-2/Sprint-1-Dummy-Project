package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.entity.ProvisioningStrategy;
import com.oneenterprise.tenant.entity.TenantProvisioning;
import com.oneenterprise.tenant.repository.TenantProvisioningRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProvisioningServiceImplTest {
    @Test void schemaPerTenant_shouldCreateDeterministicSchema() {
        TenantRepository tenants=mock(TenantRepository.class); TenantProvisioningRepository repo=mock(TenantProvisioningRepository.class); JdbcTemplate jdbc=mock(JdbcTemplate.class);
        UUID id=UUID.randomUUID(); when(tenants.existsById(id)).thenReturn(true); when(repo.findByTenantId(id)).thenReturn(Optional.empty()); when(repo.save(any())).thenAnswer(i->i.getArgument(0));
        ProvisioningServiceImpl service=new ProvisioningServiceImpl(tenants,repo,jdbc,ProvisioningStrategy.SCHEMA_PER_TENANT);
        var out=service.provision(id);
        assertEquals("PROVISIONED",out.getStatus().name()); assertEquals("SCHEMA_PER_TENANT",out.getIsolationMode().name()); assertTrue(out.getSchemaName().startsWith("tenant_"));
        ArgumentCaptor<String> sql=ArgumentCaptor.forClass(String.class); verify(jdbc).execute(sql.capture()); assertTrue(sql.getValue().contains(out.getSchemaName()));
    }
}
