package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationRequest;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.dto.TenantStatusRequest;
import com.oneenterprise.tenant.entity.Tenant;
import com.oneenterprise.tenant.entity.TenantConfiguration;
import com.oneenterprise.tenant.entity.TenantStatus;
import com.oneenterprise.tenant.exception.DuplicateResourceException;
import com.oneenterprise.tenant.exception.ResourceNotFoundException;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import com.oneenterprise.tenant.service.ProvisioningService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TenantServiceImplTest {
    @Mock TenantRepository tenantRepository;
    @Mock TenantConfigurationRepository configurationRepository;
    @Mock ProvisioningService provisioningService;
    private TenantServiceImpl service;
    private UUID tenantId;

    @BeforeEach void setUp(){ service=new TenantServiceImpl(tenantRepository,configurationRepository,provisioningService); tenantId=UUID.randomUUID(); }

    @Test void createTenant_shouldCreateDefaultConfigAndProvision() throws Exception {
        CreateTenantRequest r=new CreateTenantRequest(); r.setTenantCode("acme01"); r.setTenantName("Acme India"); r.setContactEmail("ADMIN@ACME.EXAMPLE");
        when(tenantRepository.existsByTenantCodeIgnoreCase("ACME01")).thenReturn(false);
        when(tenantRepository.save(any(Tenant.class))).thenAnswer(i->{Tenant t=i.getArgument(0);set(t,"id",tenantId);set(t,"createdAt",OffsetDateTime.now());set(t,"updatedAt",OffsetDateTime.now());return t;});
        when(configurationRepository.save(any(TenantConfiguration.class))).thenAnswer(i->i.getArgument(0));
        TenantResponse out=service.createTenant(r);
        assertEquals(tenantId,out.getId()); assertEquals("ACME01",out.getTenantCode()); assertEquals("admin@acme.example",out.getContactEmail()); assertEquals(TenantStatus.PENDING,out.getStatus());
        verify(provisioningService).provision(tenantId); verify(configurationRepository).save(any(TenantConfiguration.class));
    }

    @Test void duplicateTenantCode_shouldReject(){
        CreateTenantRequest r=new CreateTenantRequest();r.setTenantCode("ACME01");r.setTenantName("Acme");r.setContactEmail("a@b.com");
        when(tenantRepository.existsByTenantCodeIgnoreCase("ACME01")).thenReturn(true);
        assertThrows(DuplicateResourceException.class,()->service.createTenant(r)); verify(tenantRepository,never()).save(any());
    }

    @Test void pendingToActive_shouldBeAllowed(){
        Tenant t=tenant(TenantStatus.PENDING);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));when(tenantRepository.save(any())).thenAnswer(i->i.getArgument(0));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.ACTIVE);
        assertEquals(TenantStatus.ACTIVE,service.updateStatus(tenantId,r).getStatus());
    }

    @Test void activeToSuspended_shouldBeAllowed(){
        Tenant t=tenant(TenantStatus.ACTIVE);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));when(tenantRepository.save(any())).thenAnswer(i->i.getArgument(0));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.SUSPENDED);
        assertEquals(TenantStatus.SUSPENDED,service.updateStatus(tenantId,r).getStatus());
    }

    @Test void suspendedToActive_shouldBeAllowed(){
        Tenant t=tenant(TenantStatus.SUSPENDED);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));when(tenantRepository.save(any())).thenAnswer(i->i.getArgument(0));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.ACTIVE);
        assertEquals(TenantStatus.ACTIVE,service.updateStatus(tenantId,r).getStatus());
    }

    @Test void pendingToSuspended_shouldBeRejected(){
        Tenant t=tenant(TenantStatus.PENDING);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.SUSPENDED);
        IllegalArgumentException ex=assertThrows(IllegalArgumentException.class,()->service.updateStatus(tenantId,r)); assertTrue(ex.getMessage().contains("Invalid tenant status transition")); verify(tenantRepository,never()).save(any());
    }

    @Test void inactiveToActive_shouldBeRejected(){
        Tenant t=tenant(TenantStatus.INACTIVE);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.ACTIVE);
        assertThrows(IllegalArgumentException.class,()->service.updateStatus(tenantId,r));
    }

    @Test void sameStatus_shouldBeRejected(){
        Tenant t=tenant(TenantStatus.ACTIVE);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(t));
        TenantStatusRequest r=new TenantStatusRequest();r.setStatus(TenantStatus.ACTIVE);
        assertThrows(IllegalArgumentException.class,()->service.updateStatus(tenantId,r));
    }

    @Test void missingTenant_shouldReturnNotFound(){
        when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->service.getTenantById(tenantId));
    }

    @Test void configuration_shouldSupportAdvancedControls() throws Exception {
        TenantConfiguration c=new TenantConfiguration();c.setTenantId(tenantId);when(tenantRepository.findById(tenantId)).thenReturn(java.util.Optional.of(tenant(TenantStatus.ACTIVE)));when(configurationRepository.findByTenantId(tenantId)).thenReturn(java.util.Optional.of(c));when(configurationRepository.save(any())).thenAnswer(i->i.getArgument(0));
        TenantConfigurationRequest r=new TenantConfigurationRequest();r.setCurrency("usd");r.setDateFormat("yyyy-MM-dd");r.setEmailEnabled(true);r.setNotificationsEnabled(false);r.setSelfServiceEnabled(true);r.setMaxUsers(1000);r.setDataRetentionDays(730);r.setMaxStorageMb(20480);r.setSessionTimeoutMinutes(60);r.setAuditEnabled(true);r.setBackupEnabled(true);r.setBackupRetentionDays(90);r.setPasswordMinLength(14);r.setDefaultRole("manager");
        var out=service.updateConfiguration(tenantId,r);
        assertEquals("USD",out.getCurrency());assertEquals(730,out.getDataRetentionDays());assertEquals(20480,out.getMaxStorageMb());assertEquals(60,out.getSessionTimeoutMinutes());assertEquals(90,out.getBackupRetentionDays());assertEquals(14,out.getPasswordMinLength());assertEquals("MANAGER",out.getDefaultRole());
    }

    private Tenant tenant(TenantStatus status){Tenant t=new Tenant();t.setStatus(status);t.setTenantCode("ACME");t.setTenantName("Acme");t.setContactEmail("a@b.com");t.setTimezone("Asia/Kolkata");t.setLocale("en-IN");try{set(t,"id",tenantId);set(t,"createdAt",OffsetDateTime.now());set(t,"updatedAt",OffsetDateTime.now());}catch(Exception ignored){}return t;}
    private void set(Object target,String name,Object value)throws Exception{Field f=target.getClass().getDeclaredField(name);f.setAccessible(true);f.set(target,value);}
}
