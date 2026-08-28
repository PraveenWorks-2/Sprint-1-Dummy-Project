package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.CreateTenantRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationRequest;
import com.oneenterprise.tenant.dto.TenantConfigurationResponse;
import com.oneenterprise.tenant.dto.TenantResponse;
import com.oneenterprise.tenant.dto.TenantStatusRequest;
import com.oneenterprise.tenant.dto.UpdateTenantRequest;
import com.oneenterprise.tenant.entity.Tenant;
import com.oneenterprise.tenant.entity.TenantConfiguration;
import com.oneenterprise.tenant.entity.TenantStatus;
import com.oneenterprise.tenant.exception.DuplicateResourceException;
import com.oneenterprise.tenant.exception.ResourceNotFoundException;
import com.oneenterprise.tenant.repository.TenantConfigurationRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import com.oneenterprise.tenant.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantConfigurationRepository configurationRepository;

    public TenantServiceImpl(
            TenantRepository tenantRepository,
            TenantConfigurationRepository configurationRepository) {
        this.tenantRepository = tenantRepository;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public TenantResponse createTenant(CreateTenantRequest request) {
        String normalizedCode = request.getTenantCode().trim().toUpperCase();

        if (tenantRepository.existsByTenantCodeIgnoreCase(normalizedCode)) {
            throw new DuplicateResourceException(
                    "Tenant with code '" + request.getTenantCode() + "' already exists");
        }

        Tenant tenant = new Tenant();
        tenant.setTenantCode(normalizedCode);
        tenant.setTenantName(request.getTenantName().trim());
        tenant.setLegalName(trimToNull(request.getLegalName()));
        tenant.setContactEmail(request.getContactEmail().trim().toLowerCase());
        tenant.setContactPhone(trimToNull(request.getContactPhone()));
        tenant.setAddressLine1(trimToNull(request.getAddressLine1()));
        tenant.setCity(trimToNull(request.getCity()));
        tenant.setState(trimToNull(request.getState()));
        tenant.setCountry(trimToNull(request.getCountry()));
        tenant.setPostalCode(trimToNull(request.getPostalCode()));
        tenant.setTimezone(defaultIfBlank(request.getTimezone(), "Asia/Kolkata"));
        tenant.setLocale(defaultIfBlank(request.getLocale(), "en-IN"));
        tenant.setStatus(TenantStatus.PENDING);

        Tenant saved = tenantRepository.save(tenant);

        TenantConfiguration configuration = new TenantConfiguration();
        configuration.setTenantId(saved.getId());
        configurationRepository.save(configuration);

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantResponse> getAllTenants() {
        return tenantRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getTenantById(UUID id) {
        return toResponse(findTenant(id));
    }

    @Override
    public TenantResponse updateTenant(UUID id, UpdateTenantRequest request) {
        Tenant tenant = findTenant(id);
        applyUpdate(tenant, request);
        return toResponse(tenantRepository.save(tenant));
    }

    @Override
    public TenantResponse updateStatus(UUID id, TenantStatusRequest request) {
        Tenant tenant = findTenant(id);
        tenant.setStatus(request.getStatus());
        return toResponse(tenantRepository.save(tenant));
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getProfile(UUID id) {
        return toResponse(findTenant(id));
    }

    @Override
    public TenantResponse updateProfile(UUID id, UpdateTenantRequest request) {
        return updateTenant(id, request);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantResponse getStatus(UUID id) {
        return toResponse(findTenant(id));
    }

    @Override
    @Transactional(readOnly = true)
    public TenantConfigurationResponse getConfiguration(UUID id) {
        findTenant(id);
        TenantConfiguration configuration = configurationRepository.findByTenantId(id)
                .orElseGet(() -> createDefaultConfiguration(id));
        return toConfigurationResponse(configuration);
    }

    @Override
    public TenantConfigurationResponse updateConfiguration(
            UUID id, TenantConfigurationRequest request) {
        findTenant(id);

        TenantConfiguration configuration = configurationRepository.findByTenantId(id)
                .orElseGet(() -> {
                    TenantConfiguration newConfiguration = new TenantConfiguration();
                    newConfiguration.setTenantId(id);
                    return newConfiguration;
                });

        configuration.setCurrency(request.getCurrency().trim().toUpperCase());
        configuration.setDateFormat(request.getDateFormat().trim());
        configuration.setEmailEnabled(request.isEmailEnabled());
        configuration.setNotificationsEnabled(request.isNotificationsEnabled());
        configuration.setSelfServiceEnabled(request.isSelfServiceEnabled());
        configuration.setMaxUsers(request.getMaxUsers());

        return toConfigurationResponse(configurationRepository.save(configuration));
    }

    @Override
    public void deactivateTenant(UUID id) {
        Tenant tenant = findTenant(id);
        tenant.setStatus(TenantStatus.INACTIVE);
        tenantRepository.save(tenant);
    }

    private Tenant findTenant(UUID id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant with id '" + id + "' not found"));
    }

    private TenantConfiguration createDefaultConfiguration(UUID tenantId) {
        TenantConfiguration configuration = new TenantConfiguration();
        configuration.setTenantId(tenantId);
        return configurationRepository.save(configuration);
    }

    private void applyUpdate(Tenant tenant, UpdateTenantRequest request) {
        tenant.setTenantName(request.getTenantName().trim());
        tenant.setLegalName(trimToNull(request.getLegalName()));
        tenant.setContactEmail(request.getContactEmail().trim().toLowerCase());
        tenant.setContactPhone(trimToNull(request.getContactPhone()));
        tenant.setAddressLine1(trimToNull(request.getAddressLine1()));
        tenant.setCity(trimToNull(request.getCity()));
        tenant.setState(trimToNull(request.getState()));
        tenant.setCountry(trimToNull(request.getCountry()));
        tenant.setPostalCode(trimToNull(request.getPostalCode()));
        tenant.setTimezone(defaultIfBlank(request.getTimezone(), tenant.getTimezone()));
        tenant.setLocale(defaultIfBlank(request.getLocale(), tenant.getLocale()));
    }

    private TenantResponse toResponse(Tenant tenant) {
        TenantResponse response = new TenantResponse();
        response.setId(tenant.getId());
        response.setTenantCode(tenant.getTenantCode());
        response.setTenantName(tenant.getTenantName());
        response.setLegalName(tenant.getLegalName());
        response.setContactEmail(tenant.getContactEmail());
        response.setContactPhone(tenant.getContactPhone());
        response.setAddressLine1(tenant.getAddressLine1());
        response.setCity(tenant.getCity());
        response.setState(tenant.getState());
        response.setCountry(tenant.getCountry());
        response.setPostalCode(tenant.getPostalCode());
        response.setStatus(tenant.getStatus());
        response.setTimezone(tenant.getTimezone());
        response.setLocale(tenant.getLocale());
        response.setCreatedAt(tenant.getCreatedAt());
        response.setUpdatedAt(tenant.getUpdatedAt());
        return response;
    }

    private TenantConfigurationResponse toConfigurationResponse(
            TenantConfiguration configuration) {
        TenantConfigurationResponse response = new TenantConfigurationResponse();
        response.setId(configuration.getId());
        response.setTenantId(configuration.getTenantId());
        response.setCurrency(configuration.getCurrency());
        response.setDateFormat(configuration.getDateFormat());
        response.setEmailEnabled(configuration.isEmailEnabled());
        response.setNotificationsEnabled(configuration.isNotificationsEnabled());
        response.setSelfServiceEnabled(configuration.isSelfServiceEnabled());
        response.setMaxUsers(configuration.getMaxUsers());
        return response;
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }

    private String defaultIfBlank(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
