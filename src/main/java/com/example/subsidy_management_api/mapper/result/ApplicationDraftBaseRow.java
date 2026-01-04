package com.example.subsidy_management_api.mapper.result;

import java.time.LocalDate;

public class ApplicationDraftBaseRow {
  private long applicationId;
  private String applicantFullName;
  private LocalDate applicationDate;
  private long amountRequested;

  public long getApplicationId() { return applicationId; }
  public void setApplicationId(long applicationId) { this.applicationId = applicationId; }

  public String getApplicantFullName() { return applicantFullName; }
  public void setApplicantFullName(String applicantFullName) { this.applicantFullName = applicantFullName; }

  public LocalDate getApplicationDate() { return applicationDate; }
  public void setApplicationDate(LocalDate applicationDate) { this.applicationDate = applicationDate; }

  public long getAmountRequested() { return amountRequested; }
  public void setAmountRequested(long amountRequested) { this.amountRequested = amountRequested; }
}
