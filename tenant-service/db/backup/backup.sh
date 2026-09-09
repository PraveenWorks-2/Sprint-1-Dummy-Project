#!/usr/bin/env bash
set -euo pipefail
DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-oneenterprise_tenant_db}"
DB_USER="${DB_USER:-postgres}"
BACKUP_DIR="${BACKUP_DIR:-./backups}"
mkdir -p "$BACKUP_DIR"
STAMP="$(date +%Y%m%d_%H%M%S)"
pg_dump --host "$DB_HOST" --port "$DB_PORT" --username "$DB_USER" --format=custom --file "$BACKUP_DIR/${DB_NAME}_${STAMP}.dump" "$DB_NAME"
echo "Backup created: $BACKUP_DIR/${DB_NAME}_${STAMP}.dump"
