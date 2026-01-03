package com.example.subsidy_management_api.domain;

import java.time.LocalDate;


public class SubsidyApplication {

  private Long id;
  private Long applicantId;
  private LocalDate applicationDate;
  private Long amountRequested;
  private ApplicationStatus status; // まずは String（最短）。後で enum 化する

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getApplicantId() {
    return applicantId;
  }

  public void setApplicantId(Long applicantId) {
    this.applicantId = applicantId;
  }

  public LocalDate getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(LocalDate applicationDate) {
    this.applicationDate = applicationDate;
  }

  public Long getAmountRequested() {
    return amountRequested;
  }

  public void setAmountRequested(Long amountRequested) {
    this.amountRequested = amountRequested;
  }

  public ApplicationStatus getStatus() {
    return status;
  }

  public void setStatus(ApplicationStatus status) {
    this.status = status;
  }
}
