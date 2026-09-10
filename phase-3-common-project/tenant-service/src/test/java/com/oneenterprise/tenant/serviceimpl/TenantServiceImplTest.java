package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.entity.Tenant;
import com.oneenterprise.tenant.entity.TenantConfiguration;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenantServiceImplTest {

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private TenantConfigurationRepository configurationRepository;

    private TenantServiceImpl tenantService;

    @BeforeEach
    void setUp() {
        tenantService = new TenantServiceImpl(tenantRepository, configurationRepository);
    }

    @Test
    void createTenant_shouldCreateTenantAndDefaultConfiguration() throws Exception {
        CreateTenantRequest request = new CreateTenantRequest();
        request.setTenantCode("acme01");
        request.setTenantName("Acme India");
        request.setContactEmail("ADMIN@ACME.EXAMPLE");

        UUID tenantId = UUID.randomUUID();

        when(tenantRepository.existsByTenantCodeIgnoreCase("ACME01")).thenReturn(false);
        when(tenantRepository.save(any(Tenant.class))).thenAnswer(invocation -> {
            Tenant tenant = invocation.getArgument(0);
            setField(tenant, "id", tenantId);
            setField(tenant, "createdAt", OffsetDateTime.now());
            setField(tenant, "updatedAt", OffsetDateTime.now());
            return tenant;
        });
        when(configurationRepository.save(any(TenantConfiguration.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TenantResponse response = tenantService.createTenant(request);

        assertNotNull(response);
        assertEquals(tenantId, response.getId());
        assertEquals("ACME01", response.getTenantCode());
        assertEquals("admin@acme.example", response.getContactEmail());

        ArgumentCaptor<TenantConfiguration> configurationCaptor =
                ArgumentCaptor.forClass(TenantConfiguration.class);
        verify(configurationRepository).save(configurationCaptor.capture());
        assertEquals(tenantId, configurationCaptor.getValue().getTenantId());
    }

    private void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }
}
