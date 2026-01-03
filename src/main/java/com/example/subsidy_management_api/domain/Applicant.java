package com.example.subsidy_management_api.domain;

public class Applicant {
  private Long id;
  private String fullName;
  private String fullNameKana;
  private String email;
  private String phone;
  private String postalCode;
  private String address1;
  private String address2;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getFullName() { return fullName; }
  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getFullNameKana() { return fullNameKana; }
  public void setFullNameKana(String fullNameKana) { this.fullNameKana = fullNameKana; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getPostalCode() { return postalCode; }
  public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

  public String getAddress1() { return address1; }
  public void setAddress1(String address1) { this.address1 = address1; }

  public String getAddress2() { return address2; }
  public void setAddress2(String address2) { this.address2 = address2; }
}
