# Rayi Mohan - Sprint 2 Scope Mapping

Source assignment: **OneEnterprise - Sprint 2 Common Microservices**.

Assigned branch: `mohan-tenant-service`
Assigned service: Tenant Management
Focus: provisioning, isolation, backup strategy, configuration hardening.

| Assignment focus | Implementation in this project |
|---|---|
| Tenant database provisioning approach | `ProvisioningService`, `TenantProvisioning`, configurable provisioning strategy, automatic schema provisioning by default |
| Tenant isolation approach | schema-per-tenant default, tenant ID uniqueness, tenant-scoped operational APIs, isolation contract endpoint |
| Backup strategy | `TenantBackup`, retention metadata, PostgreSQL `pg_dump`/`pg_restore` scripts, production backup guidance |
| Advanced tenant configuration | retention, storage, session timeout, audit, backup controls, password policy, default role plus existing settings |
| Stronger status validation | explicit lifecycle state machine and invalid-transition HTTP 400 handling |
| Tenant lifecycle tests | JUnit/Mockito tests for creation, duplicates, valid/invalid transitions, terminal state and advanced configuration |
