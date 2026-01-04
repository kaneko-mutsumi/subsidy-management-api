package com.example.subsidy_management_api.api.dto;

import com.example.subsidy_management_api.domain.DocumentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public class DocumentIssueRequest {

  @NotNull
  private Long applicationId;

  @NotNull
  private DocumentType documentType;

  @Size(max = 50)
  private String documentNo;

  @Size(max = 100)
  private String issuedBy;

  // Draftに対して上書きしたい値（不足分だけ渡す想定）
  private Map<String, Object> overridesPayload;

  public Long getApplicationId() { return applicationId; }
  public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

  public DocumentType getDocumentType() { return documentType; }
  public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

  public String getDocumentNo() { return documentNo; }
  public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }

  public String getIssuedBy() { return issuedBy; }
  public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

  public Map<String, Object> getOverridesPayload() { return overridesPayload; }
  public void setOverridesPayload(Map<String, Object> overridesPayload) { this.overridesPayload = overridesPayload; }
}
