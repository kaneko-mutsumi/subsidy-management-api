package com.example.subsidy_management_api.api.dto;

public class ReportStatusBreakdownItem {
  private String status;
  private long count;
  private long totalAmountRequested;

  public ReportStatusBreakdownItem(String status, long count, long totalAmountRequested) {
    this.status = status;
    this.count = count;
    this.totalAmountRequested = totalAmountRequested;
  }

  public String getStatus() { return status; }
  public long getCount() { return count; }
  public long getTotalAmountRequested() { return totalAmountRequested; }
}
