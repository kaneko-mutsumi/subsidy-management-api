package com.example.subsidy_management_api.api.dto;

import java.time.OffsetDateTime;

public class DocumentListItem {
  private long documentId;
  private long applicationId;
  private String documentType;
  private String documentNo;
  private String issuedBy;
  private OffsetDateTime issuedAt;

  public long getDocumentId() { return documentId; }
  public void setDocumentId(long documentId) { this.documentId = documentId; }

  public long getApplicationId() { return applicationId; }
  public void setApplicationId(long applicationId) { this.applicationId = applicationId; }

  public String getDocumentType() { return documentType; }
  public void setDocumentType(String documentType) { this.documentType = documentType; }

  public String getDocumentNo() { return documentNo; }
  public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }

  public String getIssuedBy() { return issuedBy; }
  public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

  public OffsetDateTime getIssuedAt() { return issuedAt; }
  public void setIssuedAt(OffsetDateTime issuedAt) { this.issuedAt = issuedAt; }
}
