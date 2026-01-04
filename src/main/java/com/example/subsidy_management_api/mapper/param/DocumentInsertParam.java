package com.example.subsidy_management_api.mapper.param;

public class DocumentInsertParam {
  private Long id; // insert後にMyBatisがセットする（AUTO_INCREMENT）
  private long applicationId;
  private String documentType;
  private String documentNo;
  private String issuedBy;
  private String payloadJson;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public long getApplicationId() { return applicationId; }
  public void setApplicationId(long applicationId) { this.applicationId = applicationId; }

  public String getDocumentType() { return documentType; }
  public void setDocumentType(String documentType) { this.documentType = documentType; }

  public String getDocumentNo() { return documentNo; }
  public void setDocumentNo(String documentNo) { this.documentNo = documentNo; }

  public String getIssuedBy() { return issuedBy; }
  public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

  public String getPayloadJson() { return payloadJson; }
  public void setPayloadJson(String payloadJson) { this.payloadJson = payloadJson; }
}
