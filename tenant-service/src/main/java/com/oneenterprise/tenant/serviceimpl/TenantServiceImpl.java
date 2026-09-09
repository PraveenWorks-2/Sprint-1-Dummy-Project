package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.*;
import com.oneenterprise.tenant.entity.Tenant;
import com.oneenterprise.tenant.entity.TenantConfiguration;
import com.oneenterprise.tenant.entity.TenantStatus;
import com.oneenterprise.tenant.exception.DuplicateResourceException;
import com.oneenterprise.tenant.exception.ResourceNotFoundException;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import com.oneenterprise.tenant.service.ProvisioningService;
import com.oneenterprise.tenant.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service @Transactional
public class TenantServiceImpl implements TenantService {
    private final TenantRepository tenantRepository;
    private final TenantConfigurationRepository configurationRepository;
    private final ProvisioningService provisioningService;

    private static final Map<TenantStatus, Set<TenantStatus>> ALLOWED_TRANSITIONS = Map.of(
            TenantStatus.PENDING, EnumSet.of(TenantStatus.ACTIVE, TenantStatus.INACTIVE),
            TenantStatus.ACTIVE, EnumSet.of(TenantStatus.SUSPENDED, TenantStatus.INACTIVE),
            TenantStatus.SUSPENDED, EnumSet.of(TenantStatus.ACTIVE, TenantStatus.INACTIVE),
            TenantStatus.INACTIVE, EnumSet.noneOf(TenantStatus.class));

    public TenantServiceImpl(TenantRepository tenantRepository, TenantConfigurationRepository configurationRepository,
                             ProvisioningService provisioningService) {
        this.tenantRepository=tenantRepository; this.configurationRepository=configurationRepository; this.provisioningService=provisioningService;
    }
    @Override public TenantResponse createTenant(CreateTenantRequest request) {
        String code=request.getTenantCode().trim().toUpperCase();
        if(tenantRepository.existsByTenantCodeIgnoreCase(code)) throw new DuplicateResourceException("Tenant with code '"+request.getTenantCode()+"' already exists");
        Tenant t=new Tenant(); t.setTenantCode(code); t.setTenantName(request.getTenantName().trim()); t.setLegalName(trimToNull(request.getLegalName()));
        t.setContactEmail(request.getContactEmail().trim().toLowerCase()); t.setContactPhone(trimToNull(request.getContactPhone())); t.setAddressLine1(trimToNull(request.getAddressLine1()));
        t.setCity(trimToNull(request.getCity())); t.setState(trimToNull(request.getState())); t.setCountry(trimToNull(request.getCountry())); t.setPostalCode(trimToNull(request.getPostalCode()));
        t.setTimezone(defaultIfBlank(request.getTimezone(),"Asia/Kolkata")); t.setLocale(defaultIfBlank(request.getLocale(),"en-IN")); t.setStatus(TenantStatus.PENDING);
        Tenant saved=tenantRepository.save(t); configurationRepository.save(newDefaultConfiguration(saved.getId())); provisioningService.provision(saved.getId()); return toResponse(saved);
    }
    @Override @Transactional(readOnly=true) public List<TenantResponse> getAllTenants(){return tenantRepository.findAll().stream().map(this::toResponse).toList();}
    @Override @Transactional(readOnly=true) public TenantResponse getTenantById(UUID id){return toResponse(findTenant(id));}
    @Override public TenantResponse updateTenant(UUID id, UpdateTenantRequest request){Tenant t=findTenant(id);applyUpdate(t,request);return toResponse(tenantRepository.save(t));}
    @Override public TenantResponse updateStatus(UUID id,TenantStatusRequest request){Tenant t=findTenant(id);TenantStatus target=request.getStatus();
        if(target==null) throw new IllegalArgumentException("status is required");
        if(t.getStatus()==target) throw new IllegalArgumentException("Tenant is already in status '"+target+"'");
        if(!ALLOWED_TRANSITIONS.getOrDefault(t.getStatus(),Set.of()).contains(target)) throw new IllegalArgumentException("Invalid tenant status transition from '"+t.getStatus()+"' to '"+target+"'");
        t.setStatus(target); return toResponse(tenantRepository.save(t));}
    @Override @Transactional(readOnly=true) public TenantResponse getProfile(UUID id){return toResponse(findTenant(id));}
    @Override public TenantResponse updateProfile(UUID id,UpdateTenantRequest request){return updateTenant(id,request);}
    @Override @Transactional(readOnly=true) public TenantResponse getStatus(UUID id){return toResponse(findTenant(id));}
    @Override @Transactional(readOnly=true) public TenantConfigurationResponse getConfiguration(UUID id){findTenant(id);return toConfigurationResponse(configurationRepository.findByTenantId(id).orElseGet(()->configurationRepository.save(newDefaultConfiguration(id))));}
    @Override public TenantConfigurationResponse updateConfiguration(UUID id,TenantConfigurationRequest r){findTenant(id);TenantConfiguration c=configurationRepository.findByTenantId(id).orElseGet(()->newDefaultConfiguration(id));
        c.setCurrency(r.getCurrency().trim().toUpperCase());c.setDateFormat(r.getDateFormat().trim());c.setEmailEnabled(r.isEmailEnabled());c.setNotificationsEnabled(r.isNotificationsEnabled());c.setSelfServiceEnabled(r.isSelfServiceEnabled());
        c.setMaxUsers(r.getMaxUsers());c.setDataRetentionDays(r.getDataRetentionDays());c.setMaxStorageMb(r.getMaxStorageMb());c.setSessionTimeoutMinutes(r.getSessionTimeoutMinutes());c.setAuditEnabled(r.isAuditEnabled());c.setBackupEnabled(r.isBackupEnabled());c.setBackupRetentionDays(r.getBackupRetentionDays());c.setPasswordMinLength(r.getPasswordMinLength());c.setDefaultRole(r.getDefaultRole().trim().toUpperCase());
        return toConfigurationResponse(configurationRepository.save(c));}
    @Override public void deactivateTenant(UUID id){Tenant t=findTenant(id);if(t.getStatus()!=TenantStatus.INACTIVE){if(!ALLOWED_TRANSITIONS.get(t.getStatus()).contains(TenantStatus.INACTIVE))throw new IllegalArgumentException("Tenant cannot be deactivated from status '"+t.getStatus()+"'");t.setStatus(TenantStatus.INACTIVE);tenantRepository.save(t);}}
    private Tenant findTenant(UUID id){return tenantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Tenant with id '"+id+"' not found"));}
    private TenantConfiguration newDefaultConfiguration(UUID id){TenantConfiguration c=new TenantConfiguration();c.setTenantId(id);return c;}
    private void applyUpdate(Tenant t,UpdateTenantRequest r){t.setTenantName(r.getTenantName().trim());t.setLegalName(trimToNull(r.getLegalName()));t.setContactEmail(r.getContactEmail().trim().toLowerCase());t.setContactPhone(trimToNull(r.getContactPhone()));t.setAddressLine1(trimToNull(r.getAddressLine1()));t.setCity(trimToNull(r.getCity()));t.setState(trimToNull(r.getState()));t.setCountry(trimToNull(r.getCountry()));t.setPostalCode(trimToNull(r.getPostalCode()));t.setTimezone(defaultIfBlank(r.getTimezone(),t.getTimezone()));t.setLocale(defaultIfBlank(r.getLocale(),t.getLocale()));}
    private TenantResponse toResponse(Tenant t){TenantResponse r=new TenantResponse();r.setId(t.getId());r.setTenantCode(t.getTenantCode());r.setTenantName(t.getTenantName());r.setLegalName(t.getLegalName());r.setContactEmail(t.getContactEmail());r.setContactPhone(t.getContactPhone());r.setAddressLine1(t.getAddressLine1());r.setCity(t.getCity());r.setState(t.getState());r.setCountry(t.getCountry());r.setPostalCode(t.getPostalCode());r.setStatus(t.getStatus());r.setTimezone(t.getTimezone());r.setLocale(t.getLocale());r.setCreatedAt(t.getCreatedAt());r.setUpdatedAt(t.getUpdatedAt());return r;}
    private TenantConfigurationResponse toConfigurationResponse(TenantConfiguration c){TenantConfigurationResponse r=new TenantConfigurationResponse();r.setId(c.getId());r.setTenantId(c.getTenantId());r.setCurrency(c.getCurrency());r.setDateFormat(c.getDateFormat());r.setEmailEnabled(c.isEmailEnabled());r.setNotificationsEnabled(c.isNotificationsEnabled());r.setSelfServiceEnabled(c.isSelfServiceEnabled());r.setMaxUsers(c.getMaxUsers());r.setDataRetentionDays(c.getDataRetentionDays());r.setMaxStorageMb(c.getMaxStorageMb());r.setSessionTimeoutMinutes(c.getSessionTimeoutMinutes());r.setAuditEnabled(c.isAuditEnabled());r.setBackupEnabled(c.isBackupEnabled());r.setBackupRetentionDays(c.getBackupRetentionDays());r.setPasswordMinLength(c.getPasswordMinLength());r.setDefaultRole(c.getDefaultRole());return r;}
    private String trimToNull(String v){return v==null||v.isBlank()?null:v.trim();} private String defaultIfBlank(String v,String d){return v==null||v.isBlank()?d:v.trim();}
}
