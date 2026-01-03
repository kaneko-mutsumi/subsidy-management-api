package com.example.subsidy_management_api.api.dto;

public class SubsidyApplicationCreateResponse {
  private long applicantId;
  private long applicationId;

  public SubsidyApplicationCreateResponse(long applicantId, long applicationId) {
    this.applicantId = applicantId;
    this.applicationId = applicationId;
  }

  public long getApplicantId() { return applicantId; }
  public long getApplicationId() { return applicationId; }
}
