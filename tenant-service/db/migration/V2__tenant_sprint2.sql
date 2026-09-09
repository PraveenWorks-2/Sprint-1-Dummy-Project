-- Sprint 2 reference migration for environments that use Flyway/Liquibase.
-- The application currently uses Hibernate ddl-auto=update for local development.
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS data_retention_days integer DEFAULT 365;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS max_storage_mb integer DEFAULT 10240;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS session_timeout_minutes integer DEFAULT 30;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS audit_enabled boolean DEFAULT true;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS backup_enabled boolean DEFAULT true;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS backup_retention_days integer DEFAULT 30;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS password_min_length integer DEFAULT 12;
ALTER TABLE IF EXISTS tenant_configurations ADD COLUMN IF NOT EXISTS default_role varchar(50) DEFAULT 'USER';

CREATE TABLE IF NOT EXISTS tenant_provisioning (
    id uuid PRIMARY KEY,
    tenant_id uuid NOT NULL UNIQUE,
    strategy varchar(30) NOT NULL,
    isolation_mode varchar(30) NOT NULL,
    status varchar(20) NOT NULL,
    schema_name varchar(63),
    database_name varchar(100),
    failure_reason varchar(500),
    provisioned_at timestamptz,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL
);
CREATE TABLE IF NOT EXISTS tenant_backups (
    id uuid PRIMARY KEY,
    tenant_id uuid NOT NULL,
    status varchar(20) NOT NULL,
    backup_type varchar(30) NOT NULL,
    location varchar(300) NOT NULL,
    retention_until timestamptz,
    created_at timestamptz NOT NULL
);
