package com.example.subsidy_management_api.api.dto;

import java.time.LocalDate;

public class SubsidyApplicationDetailResponse {
  private long applicationId;
  private long applicantId;

  private String applicantFullName;
  private String applicantFullNameKana;
  private String applicantEmail;
  private String applicantPhone;
  private String applicantPostalCode;
  private String applicantAddress1;
  private String applicantAddress2;

  private LocalDate applicationDate;
  private long amountRequested;
  private String status;
  private LocalDate decisionDate;
  private Long approvedAmount;
  private LocalDate paidDate;
  private String note;

  public SubsidyApplicationDetailResponse(
      long applicationId,
      long applicantId,
      String applicantFullName,
      String applicantFullNameKana,
      String applicantEmail,
      String applicantPhone,
      String applicantPostalCode,
      String applicantAddress1,
      String applicantAddress2,
      LocalDate applicationDate,
      long amountRequested,
      String status,
      LocalDate decisionDate,
      Long approvedAmount,
      LocalDate paidDate,
      String note
  ) {
    this.applicationId = applicationId;
    this.applicantId = applicantId;
    this.applicantFullName = applicantFullName;
    this.applicantFullNameKana = applicantFullNameKana;
    this.applicantEmail = applicantEmail;
    this.applicantPhone = applicantPhone;
    this.applicantPostalCode = applicantPostalCode;
    this.applicantAddress1 = applicantAddress1;
    this.applicantAddress2 = applicantAddress2;
    this.applicationDate = applicationDate;
    this.amountRequested = amountRequested;
    this.status = status;
    this.decisionDate = decisionDate;
    this.approvedAmount = approvedAmount;
    this.paidDate = paidDate;
    this.note = note;
  }

  public long getApplicationId() { return applicationId; }
  public long getApplicantId() { return applicantId; }
  public String getApplicantFullName() { return applicantFullName; }
  public String getApplicantFullNameKana() { return applicantFullNameKana; }
  public String getApplicantEmail() { return applicantEmail; }
  public String getApplicantPhone() { return applicantPhone; }
  public String getApplicantPostalCode() { return applicantPostalCode; }
  public String getApplicantAddress1() { return applicantAddress1; }
  public String getApplicantAddress2() { return applicantAddress2; }
  public LocalDate getApplicationDate() { return applicationDate; }
  public long getAmountRequested() { return amountRequested; }
  public String getStatus() { return status; }
  public LocalDate getDecisionDate() { return decisionDate; }
  public Long getApprovedAmount() { return approvedAmount; }
  public LocalDate getPaidDate() { return paidDate; }
  public String getNote() { return note; }
}
