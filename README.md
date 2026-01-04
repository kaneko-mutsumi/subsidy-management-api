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


## Reports

### GET /reports/summary
申請件数と申請額合計のサマリーを返します。

#### Query parameters（任意）
- `from` (YYYY-MM-DD): 申請日（application_date）の開始日（含む）
- `to` (YYYY-MM-DD): 申請日（application_date）の終了日（含む）
- `status`: ステータス（例: `APPLIED`）

#### 仕様（重要）
- `from` を省略した場合：開始日の条件なし（全期間）
- `to` を省略した場合：終了日の条件なし（全期間）
- `status` を省略した場合：ステータス条件なし（全ステータス）
- つまり、**パラメータ未指定の場合は全件集計**になります。

#### Examples
- 全件集計（全期間・全ステータス）
    - `GET /reports/summary`
- ステータス絞り込み
    - `GET /reports/summary?status=APPLIED`
- 期間指定（全ステータス）
    - `GET /reports/summary?from=2026-01-01&to=2026-01-31`
- 期間 + ステータス
    - `GET /reports/summary?status=APPLIED&from=2026-01-01&to=2026-01-31`

#### Response example
```json
{
  "from": null,
  "to": null,
  "status": "APPLIED",
  "count": 1,
  "totalAmountRequested": 150000
}
```

## Documents payload template（差し込み項目テンプレ）

`POST /documents` の `payload` は、`documentType` に応じて最低限の必須キーを要求します。
必須キーが不足している場合は `400 Bad Request` を返します。

### DECISION_NOTICE（交付決定通知）
必須キー：
- `applicantFullName`
- `applicationDate` (YYYY-MM-DD)
- `amountRequested` (number)
- `decisionDate` (YYYY-MM-DD)
- `approvedAmount` (number)

### SETTLEMENT_NOTICE（確定通知）
必須キー：
- `applicantFullName`
- `applicationDate` (YYYY-MM-DD)
- `amountRequested` (number)
- `settlementDate` (YYYY-MM-DD)
- `approvedAmount` (number)

### SURVEY_REQUEST（調査依頼）
必須キー：
- `applicantFullName`
- `applicationDate` (YYYY-MM-DD)
- `amountRequested` (number)
- `surveyDueDate` (YYYY-MM-DD)

### Example（POST /documents）
```json
{
  "applicationId": 2,
  "documentType": "DECISION_NOTICE",
  "documentNo": "R8-001",
  "issuedBy": "city-staff",
  "payload": {
    "applicantFullName": "佐藤花子",
    "applicationDate": "2026-01-05",
    "amountRequested": 150000,
    "decisionDate": "2026-01-10",
    "approvedAmount": 150000
  }
}
```