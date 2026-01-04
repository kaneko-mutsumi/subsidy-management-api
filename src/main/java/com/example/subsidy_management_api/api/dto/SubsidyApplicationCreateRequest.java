package com.example.subsidy_management_api.api.dto;

import com.example.subsidy_management_api.domain.ApplicationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SubsidyApplicationCreateRequest {

  @NotNull
  @Valid
  private ApplicantInput applicant;

  @NotNull
  @Valid
  private ApplicationInput application;

  public ApplicantInput getApplicant() {
    return applicant;
  }

  public void setApplicant(ApplicantInput applicant) {
    this.applicant = applicant;
  }

  public ApplicationInput getApplication() {
    return application;
  }

  public void setApplicationInput(ApplicationInput application) {
    this.application = application;
  }

  public void setApplication(ApplicationInput application) {
    this.application = application;
  }

  public static class ApplicantInput {

    @NotBlank
    private String fullName;

    private String fullNameKana;
    private String email;
    private String phone;
    private String postalCode;
    private String address1;
    private String address2;

    public String getFullName() {
      return fullName;
    }

    public void setFullName(String fullName) {
      this.fullName = fullName;
    }

    public String getFullNameKana() {
      return fullNameKana;
    }

    public void setFullNameKana(String fullNameKana) {
      this.fullNameKana = fullNameKana;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getPhone() {
      return phone;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    public String getAddress1() {
      return address1;
    }

    public void setAddress1(String address1) {
      this.address1 = address1;
    }

    public String getAddress2() {
      return address2;
    }

    public void setAddress2(String address2) {
      this.address2 = address2;
    }
  }

  public static class ApplicationInput {

    @NotBlank
    private String applicationDate; // まずは文字列で受ける（最短）

    @NotNull
    @Positive
    private Long amountRequested;

    @NotNull
    private ApplicationStatus status;

    public String getApplicationDate() {
      return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
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
}
