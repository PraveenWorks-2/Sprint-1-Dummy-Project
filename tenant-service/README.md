# OneEnterprise Tenant Management Service - Sprint 2

**Developer:** Rayi Mohan
**Branch:** `mohan-tenant-service`
**Java:** 21
**Spring Boot:** 3.5.6
**Database:** PostgreSQL
**Port:** 8083

## Sprint 2 scope completed
This project extends the completed Tenant Management baseline without rebuilding it. It adds:
- PostgreSQL tenant provisioning approach with configurable strategy.
- Schema-per-tenant isolation by default.
- Provisioning status and operational verification APIs.
- Per-tenant backup request/retention metadata and PostgreSQL backup/restore scripts.
- Advanced tenant configuration controls.
- Strong tenant lifecycle/status transition validation.
- Lifecycle-focused JUnit/Mockito tests.
- Updated Postman collection and design documentation.

## Run in Eclipse
1. Import the folder containing `pom.xml` as an **Existing Maven Project**.
2. Ensure Java 21 is configured.
3. Create PostgreSQL database `oneenterprise_tenant_db`.
4. Set `DB_PASSWORD` if your local PostgreSQL password is not the default used by the sample configuration.
5. Run `TenantServiceApplication`.
6. Verify `GET http://localhost:8083/actuator/health`.

## Existing APIs
- `POST /api/tenants`
- `GET /api/tenants`
- `GET /api/tenants/{id}`
- `PUT /api/tenants/{id}`
- `GET /api/tenants/{id}/profile`
- `PUT /api/tenants/{id}/profile`
- `GET /api/tenants/{id}/status`
- `PATCH /api/tenants/{id}/status`
- `GET /api/tenants/{id}/configuration`
- `PUT /api/tenants/{id}/configuration`
- `DELETE /api/tenants/{id}`

## Sprint 2 APIs
### Provisioning
`POST /api/tenants/{id}/provisioning`
`GET /api/tenants/{id}/provisioning`

Default strategy: `SCHEMA_PER_TENANT`. A deterministic PostgreSQL schema is created for each tenant.

### Isolation
`GET /api/tenants/{id}/isolation`

Returns the tenant's database/schema isolation contract.

### Backup
`POST /api/tenants/{id}/backups`
`GET /api/tenants/{id}/backups`

The API records a backup request and retention metadata. Actual production dump execution is externalized to PostgreSQL backup automation. See `db/backup`.

## Advanced configuration example
```json
{
  "currency": "INR",
  "dateFormat": "dd-MM-yyyy",
  "emailEnabled": true,
  "notificationsEnabled": true,
  "selfServiceEnabled": true,
  "maxUsers": 500,
  "dataRetentionDays": 730,
  "maxStorageMb": 20480,
  "sessionTimeoutMinutes": 60,
  "auditEnabled": true,
  "backupEnabled": true,
  "backupRetentionDays": 90,
  "passwordMinLength": 14,
  "defaultRole": "USER"
}
```

## Status lifecycle
Allowed transitions:
- `PENDING -> ACTIVE | INACTIVE`
- `ACTIVE -> SUSPENDED | INACTIVE`
- `SUSPENDED -> ACTIVE | INACTIVE`
- `INACTIVE -> terminal`

Same-status and invalid transitions return HTTP 400.

## Backup strategy
For production, use encrypted, off-host PostgreSQL custom-format dumps with retention according to tenant configuration. Scripts:
- `db/backup/backup.sh`
- `db/backup/restore.sh`
- `db/backup/backup.ps1`

Never commit real database passwords.

## Tests
The test suite covers tenant creation, default configuration/provisioning invocation, duplicate tenant codes, valid lifecycle transitions, invalid lifecycle transitions, terminal inactive state, missing tenants, and advanced configuration.

## Documentation
See `docs/SPRINT2_TENANT_DESIGN.md` for the provisioning, isolation, backup and lifecycle design.
