## Local development (MySQL via Docker)

### 1. Start MySQL
```powershell
Copy-Item .env.example .env
docker compose up -d
docker compose ps
```

### 2. Load env vars (PowerShell)
```powershell
Get-Content .env | ForEach-Object {
  if ($_ -match '^\s*$' -or $_ -match '^\s*#') { return }
  $k, $v = $_ -split '=', 2
  [Environment]::SetEnvironmentVariable($k.Trim(), $v.Trim(), "Process")
}
```

### 3. Run with local profile
```powershell
.\gradlew bootRun --args="--spring.profiles.active=local"
```