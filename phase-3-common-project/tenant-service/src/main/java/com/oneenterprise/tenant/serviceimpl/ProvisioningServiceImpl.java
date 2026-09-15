package com.oneenterprise.tenant.serviceimpl;

import com.oneenterprise.tenant.dto.ProvisioningResponse;
import com.oneenterprise.tenant.entity.*;
import com.oneenterprise.tenant.exception.ResourceNotFoundException;
import com.oneenterprise.tenant.repository.TenantProvisioningRepository;
import com.oneenterprise.tenant.repository.TenantRepository;
import com.oneenterprise.tenant.service.ProvisioningService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Transactional
public class ProvisioningServiceImpl implements ProvisioningService {
    private final TenantRepository tenantRepository; private final TenantProvisioningRepository repository; private final JdbcTemplate jdbc;
    private final ProvisioningStrategy strategy;
    public ProvisioningServiceImpl(TenantRepository tenantRepository, TenantProvisioningRepository repository, JdbcTemplate jdbc,
                                    @Value("${tenant.provisioning.strategy:SCHEMA_PER_TENANT}") ProvisioningStrategy strategy) {
        this.tenantRepository=tenantRepository; this.repository=repository; this.jdbc=jdbc; this.strategy=strategy;
    }
    @Override public ProvisioningResponse provision(UUID tenantId) {
        if (!tenantRepository.existsById(tenantId)) throw new ResourceNotFoundException("Tenant with id '"+tenantId+"' not found");
        TenantProvisioning p=repository.findByTenantId(tenantId).orElseGet(TenantProvisioning::new);
        p.setTenantId(tenantId); p.setStrategy(strategy);
        p.setIsolationMode(switch(strategy){case SHARED_DATABASE -> IsolationMode.SHARED_SCHEMA; case SCHEMA_PER_TENANT -> IsolationMode.SCHEMA_PER_TENANT; case DEDICATED_DATABASE -> IsolationMode.DEDICATED_DATABASE;});
        p.setStatus(ProvisioningStatus.PENDING); p.setFailureReason(null);
        try {
            if(strategy==ProvisioningStrategy.SCHEMA_PER_TENANT){
                String schema="tenant_"+tenantId.toString().replace("-", "");
                jdbc.execute("CREATE SCHEMA IF NOT EXISTS \""+schema+"\""); p.setSchemaName(schema);
            } else if(strategy==ProvisioningStrategy.SHARED_DATABASE){ p.setSchemaName("public"); }
            else { p.setDatabaseName("dedicated-"+tenantId); }
            p.setStatus(ProvisioningStatus.PROVISIONED); p.setProvisionedAt(OffsetDateTime.now());
        } catch(Exception ex){ p.setStatus(ProvisioningStatus.FAILED); p.setFailureReason("Provisioning failed: "+ex.getMessage()); }
        return toResponse(repository.save(p));
    }
    @Override @Transactional(readOnly=true) public ProvisioningResponse get(UUID tenantId){
        return repository.findByTenantId(tenantId).map(this::toResponse).orElseThrow(()->new ResourceNotFoundException("Provisioning record for tenant '"+tenantId+"' not found"));
    }
    private ProvisioningResponse toResponse(TenantProvisioning p){ ProvisioningResponse r=new ProvisioningResponse(); r.setId(p.getId()); r.setTenantId(p.getTenantId()); r.setStrategy(p.getStrategy()); r.setIsolationMode(p.getIsolationMode()); r.setStatus(p.getStatus()); r.setSchemaName(p.getSchemaName()); r.setDatabaseName(p.getDatabaseName()); r.setFailureReason(p.getFailureReason()); r.setProvisionedAt(p.getProvisionedAt()); return r; }
}
