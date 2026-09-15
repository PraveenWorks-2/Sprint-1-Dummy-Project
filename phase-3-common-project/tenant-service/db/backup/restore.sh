#!/usr/bin/env bash
set -euo pipefail
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-oneenterprise_tenant_db}"
DB_USER="${DB_USER:-postgres}"
BACKUP_FILE="${1:?Usage: restore.sh <backup-file>}"
pg_restore --host "$DB_HOST" --port "$DB_PORT" --username "$DB_USER" --dbname "$DB_NAME" --clean --if-exists "$BACKUP_FILE"
echo "Restore completed from: $BACKUP_FILE"
