# subsidy-management-api

自治体業務を想定した「補助金申請の管理システム」風の Web API（ポートフォリオ）です。  
申請データの検索・集計（議会報告）をスムーズにし、通知書の作成業務（転記入力）を **Draft（自動入力）＋不足分だけ上書き** で簡略化することを目的にしています。

---

## 目的（What this API solves）
- **申請データの検索・一覧**を素早く行い、申請件数や申請額などの報告を簡単にする
- **通知書発行（交付決定通知／確定通知／調査依頼など）** の転記入力を減らす
    - Draft（下書き自動生成）→ Issue（不足分だけ入力して発行保存）

---

## 技術スタック
- Java 21
- Spring Boot
- MyBatis
- MySQL 8.x（Local: Docker / 将来: RDS想定）
- Flyway（DBマイグレーション）
- Gradle
- Testing: JUnit5 / Spring Boot Test
- GitHub Actions（CI）

---

## データモデル（概要）
- `applicants`：申請者（論理削除：`deleted_at`）
- `subsidy_applications`：補助金申請（論理削除：`deleted_at`）
- `documents`：通知書等の発行記録（payload を JSON 保存）
- `audit_logs`：監査ログ（将来拡張）

---

## Local development（MySQL via Docker）

### 1. MySQL 起動
```powershell
Copy-Item .env.example .env
docker compose up -d
docker compose ps
```

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

### 4. Health check
GET `http://localhost:8080/actuator/health`

→ `{"status":"UP"}`

## Test（H2）

テストは H2（in-memory）で動かします。
※ `src/test/resources/application.yml` に H2 設定があり、Flyway はテストでは無効化しています。
```powershell
.\gradlew test
```
---

## API Overview
### Subsidy Applications

・`POST /subsidy-applications`
申請＋申請者を1回で登録（トランザクション）

・`GET /subsidy-applications`
一覧・検索（status/from/to/q）

・`GET /subsidy-applications/{id}`
詳細取得

・`DELETE /subsidy-applications/{id}`
論理削除（D in CRUD）

---

## Reports

### `GET /reports/summary`
件数・合計額のサマリーを返します。
- パラメータ未指定 = **全件集計**
- `status` / `from` / `to` 指定可

### `GET /reports/breakdown-by-status`
status 別内訳（GROUP BY）を返します。

## Documents（通知書の発行記録）

### `POST /documents`
発行記録を直接登録（payload を JSON 保存）

### `GET /documents?applicationId={id}`
申請IDに紐づく発行履歴を取得

### `GET /documents/draft?applicationId={id}&documentType={type}`
payload 下書きを自動生成（入力簡略の第一段階）

### `POST /documents/issue`
Draft＋不足分だけ入力して発行保存（入力簡略の完成）

---

## Documents：入力簡略フロー（Draft → Issue）

### 1) Draft（下書きを作る）
申請＋申請者情報から、payload のうち自動入力できる項目を埋め、必須キーは全て返します。  
DBだけでは確定できない項目は `null` のまま返ります。

**例：**
```text
GET /documents/draft?applicationId=2&documentType=DECISION_NOTICE
```

レスポンス例：
```json
{
  "applicationId": 2,
  "documentType": "DECISION_NOTICE",
  "payload": {
    "applicantFullName": "佐藤花子",
    "applicationDate": "2026-01-05",
    "amountRequested": 150000,
    "decisionDate": null,
    "approvedAmount": null
  }
}
```

### 2) Issue（不足分だけ入力して発行保存する）

Draft で null だった項目だけ overridesPayload で渡して発行保存します。
サーバ側で Draft生成 → overrides上書き → 必須項目検証 → documents保存 の順で処理されます。

`POST /documents/issue`

```json
{
  "applicationId": 2,
  "documentType": "DECISION_NOTICE",
  "documentNo": "R8-001",
  "issuedBy": "city-staff",
  "overridesPayload": {
    "decisionDate": "2026-01-10",
    "approvedAmount": 150000
  }
}
```

期待：
　・201 Created
　・documentId が返る
　・payload に自動入力＋上書き結果が含まれる

### 3) 発行履歴確認

`GET /documents?applicationId=2`

→ 発行記録が増えていることを確認できます（issued_at 降順）

---
## Documents payload template（差し込み項目テンプレ）

`POST /documents` / `POST /documents/issue` の payload は `documentType` ごとに最低限の必須キーを要求します。
必須キーが不足している場合は `400 Bad Request` を返します。

## DECISION_NOTICE（交付決定通知）
必須キー：

・`applicantFullName`
・`applicationDate (YYYY-MM-DD)`
・`amountRequested (number)`
・`decisionDate (YYYY-MM-DD)`
・`approvedAmount (number)`

## SETTLEMENT_NOTICE（確定通知）
必須キー：
・`applicantFullName`
・`applicationDate (YYYY-MM-DD)`
・`approvedAmount (number)`
・`settlementDate (YYYY-MM-DD)`
・`settledAmount (number)`

## SURVEY_REQUEST（調査依頼）
必須キー：
・`applicantFullName`
・`applicationDate (YYYY-MM-DD)`
・`amountRequested (number)`
・`surverDueDate (YYYY-MM-DD)`

---

## Error response（例）
・必須キー不足など：400 Bad Request
・申請が存在しない：404 Not Found
・想定外：500 Internal Server Error

```json
{
  "code": "BAD_REQUEST",
  "message": "payload is missing required keys: decisionDate",
  "timestamp": "2026-01-04T17:28:07.442422+09:00"
}
```
