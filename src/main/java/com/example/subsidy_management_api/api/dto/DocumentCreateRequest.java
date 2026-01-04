package com.example.subsidy_management_api.api.dto;

import com.example.subsidy_management_api.domain.DocumentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public class DocumentCreateRequest {

  @NotNull
  private Long applicationId;

  @NotNull
  private DocumentType documentType;

  @Size(max = 50)
  private String documentNo;

  @Size(max = 100)
  private String issuedBy;

  // 差し込み用（帳票に入れる値）をJSONとして保持する
  private Map<String, Object> payload;

  public Long getApplicationId() { return applicationId; }
  public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

  public DocumentType getDocumentType() { return documentType; }
  public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

  public String getDocumentNo() { return documentNo; }
  public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }

  public String getIssuedBy() { return issuedBy; }
  public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

  public Map<String, Object> getPayload() { return payload; }
  public void setPayload(Map<String, Object> payload) { this.payload = payload; }
}
