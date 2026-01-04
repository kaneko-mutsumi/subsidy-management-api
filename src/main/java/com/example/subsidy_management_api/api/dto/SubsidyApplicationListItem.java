package com.example.subsidy_management_api.api.dto;

import java.time.LocalDate;

public class SubsidyApplicationListItem {
  private long applicationId;
  private long applicantId;
  private String applicantFullName;
  private LocalDate applicationDate;
  private long amountRequested;
  private String status;

  public SubsidyApplicationListItem(long applicationId, long applicantId, String applicantFullName,
      LocalDate applicationDate, long amountRequested, String status) {
    this.applicationId = applicationId;
    this.applicantId = applicantId;
    this.applicantFullName = applicantFullName;
    this.applicationDate = applicationDate;
    this.amountRequested = amountRequested;
    this.status = status;
  }

  public long getApplicationId() { return applicationId; }
  public long getApplicantId() { return applicantId; }
  public String getApplicantFullName() { return applicantFullName; }
  public LocalDate getApplicationDate() { return applicationDate; }
  public long getAmountRequested() { return amountRequested; }
  public String getStatus() { return status; }

public SubsidyApplicationListItem() {
}

public void setApplicationId(long applicationId) { this.applicationId = applicationId; }
public void setApplicantId(long applicantId) { this.applicantId = applicantId; }
public void setApplicantFullName(String applicantFullName) { this.applicantFullName = applicantFullName; }
public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }
public void setAmountRequested(long amountRequested) { this.amountRequested = amountRequested; }
public void setStatus(String status) { this.status = status; }
}
