package com.example.subsidy_management_api.api.dto;

import java.util.Map;

public class DocumentDraftResponse {
  private long applicationId;
  private String documentType;
  private Map<String, Object> payload;

  public DocumentDraftResponse(long applicationId, String documentType, Map<String, Object> payload) {
    this.applicationId = applicationId;
    this.documentType = documentType;
    this.payload = payload;
  }

  public long getApplicationId() { return applicationId; }
  public String getDocumentType() { return documentType; }
  public Map<String, Object> getPayload() { return payload; }
}
