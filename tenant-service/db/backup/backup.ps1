param([string]$BackupDir = ".\backups")
$DbHost = if ($env:DB_HOST) { $env:DB_HOST } else { "localhost" }
$DbPort = if ($env:DB_PORT) { $env:DB_PORT } else { "5432" }
$DbName = if ($env:DB_NAME) { $env:DB_NAME } else { "oneenterprise_tenant_db" }
$DbUser = if ($env:DB_USER) { $env:DB_USER } else { "postgres" }
New-Item -ItemType Directory -Force -Path $BackupDir | Out-Null
$stamp = Get-Date -Format "yyyyMMdd_HHmmss"
$file = Join-Path $BackupDir "${DbName}_${stamp}.dump"
pg_dump --host $DbHost --port $DbPort --username $DbUser --format custom --file $file $DbName
Write-Host "Backup created: $file"
