package com.example.subsidy_management_api.api.dto;

import java.time.OffsetDateTime;

public class DocumentCreateResponse {
  private long documentId;
  private long applicationId;
  private String documentType;
  private OffsetDateTime issuedAt;

  public DocumentCreateResponse(long documentId, long applicationId, String documentType, OffsetDateTime issuedAt) {
    this.documentId = documentId;
    this.applicationId = applicationId;
    this.documentType = documentType;
    this.issuedAt = issuedAt;
  }

  public long getDocumentId() { return documentId; }
  public long getApplicationId() { return applicationId; }
  public String getDocumentType() { return documentType; }
  public OffsetDateTime getIssuedAt() { return issuedAt; }
}
