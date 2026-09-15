# Sprint 2 Tenant Test Checklist

| Area | Scenario | Expected |
|---|---|---|
| Provisioning | Create tenant | 201 + provisioning record PROVISIONED |
| Provisioning | Re-run provisioning | Same tenant record is reused and remains PROVISIONED |
| Isolation | Get isolation | SCHEMA_PER_TENANT and tenant-specific schema |
| Configuration | Valid advanced configuration | 200 and all values persisted |
| Configuration | Invalid min/max values | 400 validation response |
| Lifecycle | PENDING -> ACTIVE | Allowed |
| Lifecycle | ACTIVE -> SUSPENDED | Allowed |
| Lifecycle | SUSPENDED -> ACTIVE | Allowed |
| Lifecycle | SUSPENDED -> PENDING | 400 |
| Lifecycle | INACTIVE -> ACTIVE | 400 |
| Lifecycle | Same status | 400 |
| Backup | Request backup | 202 + REQUESTED metadata |
| Backup | Backup history | 200 + tenant-scoped records |
| Regression | Existing profile/status/config APIs | Continue to work |

## PostgreSQL verification
```sql
SELECT id, tenant_code, status FROM tenants ORDER BY created_at DESC;
SELECT tenant_id, strategy, isolation_mode, status, schema_name FROM tenant_provisioning;
SELECT tenant_id, currency, data_retention_days, max_storage_mb, backup_retention_days FROM tenant_configurations;
SELECT tenant_id, status, backup_type, location, retention_until FROM tenant_backups ORDER BY created_at DESC;
```
