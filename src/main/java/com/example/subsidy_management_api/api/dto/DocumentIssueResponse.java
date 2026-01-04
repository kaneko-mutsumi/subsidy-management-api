package com.example.subsidy_management_api.api.dto;

import java.time.OffsetDateTime;
import java.util.Map;

public class DocumentIssueResponse {
  private long documentId;
  private long applicationId;
  private String documentType;
  private OffsetDateTime issuedAt;
  private Map<String, Object> payload;

  public DocumentIssueResponse(long documentId, long applicationId, String documentType, OffsetDateTime issuedAt, Map<String, Object> payload) {
    this.documentId = documentId;
    this.applicationId = applicationId;
    this.documentType = documentType;
    this.issuedAt = issuedAt;
    this.payload = payload;
  }

  public long getDocumentId() { return documentId; }
  public long getApplicationId() { return applicationId; }
  public String getDocumentType() { return documentType; }
  public OffsetDateTime getIssuedAt() { return issuedAt; }
  public Map<String, Object> getPayload() { return payload; }
}
