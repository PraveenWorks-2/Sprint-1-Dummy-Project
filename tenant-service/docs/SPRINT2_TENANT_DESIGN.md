# Sprint 2 Tenant Management Design

## Scope
This extension preserves the completed Tenant Management APIs and adds the assigned Sprint 2 depth: database provisioning, tenant isolation, backup strategy, advanced configuration, stronger status validation, and lifecycle tests.

## 1. Database provisioning approach
The default local strategy is `SCHEMA_PER_TENANT`.
- A tenant is created in the shared PostgreSQL database.
- A deterministic PostgreSQL schema named `tenant_<tenant-uuid-without-hyphens>` is provisioned automatically.
- Provisioning is recorded in `tenant_provisioning` with strategy, isolation mode, status and schema/database metadata.
- The strategy can be changed with `TENANT_PROVISIONING_STRATEGY` / `tenant.provisioning.strategy` to `SHARED_DATABASE`, `SCHEMA_PER_TENANT`, or `DEDICATED_DATABASE`.
- Dedicated database provisioning is represented as an enterprise deployment contract; actual database creation should be performed by infrastructure automation rather than by an application request.

## 2. Tenant isolation approach
- Every tenant-scoped record contains a tenant ID.
- Tenant configuration and provisioning records use a unique tenant ID relationship/index.
- Schema-per-tenant provisioning gives each tenant a PostgreSQL schema boundary for tenant-owned data.
- Cross-tenant operations must resolve and validate the target tenant before reading/updating tenant resources.
- The `/isolation` endpoint exposes the active isolation contract for operational verification.

## 3. Backup strategy
The service records per-tenant backup requests and retention metadata in `tenant_backups`.
- Backup type: `POSTGRESQL_LOGICAL`.
- Retention is controlled by `backupRetentionDays` in tenant configuration.
- Deployment backup execution is intentionally externalized to `pg_dump`/backup automation; scripts are provided under `db/backup`.
- Use custom-format PostgreSQL dumps for restore flexibility.
- Store backups outside the application host in production and encrypt them using the organization's storage/KMS policy.

## 4. Advanced tenant configuration
Added controls include data retention, storage limit, session timeout, audit enablement, backup enablement/retention, password minimum length and default role, while retaining currency, date format, email, notifications, self-service and max users.

## 5. Stronger status validation
Allowed lifecycle transitions:
- `PENDING -> ACTIVE | INACTIVE`
- `ACTIVE -> SUSPENDED | INACTIVE`
- `SUSPENDED -> ACTIVE | INACTIVE`
- `INACTIVE -> terminal`

The same-status transition is rejected. Invalid transitions return HTTP 400. This prevents accidental resurrection of an inactive tenant and prevents direct PENDING -> SUSPENDED.

## 6. Lifecycle tests
The test suite covers creation/default configuration, duplicate code rejection, valid status transitions, invalid transitions, terminal inactive state, missing tenant handling and configuration validation boundaries.
