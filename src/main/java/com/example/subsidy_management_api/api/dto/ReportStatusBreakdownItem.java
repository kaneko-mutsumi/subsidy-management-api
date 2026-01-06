package com.example.subsidy_management_api.api.dto;

public class ReportStatusBreakdownItem {
  private String status;
  private long count;
  private long totalAmountRequested;

  public ReportStatusBreakdownItem(){}

  public ReportStatusBreakdownItem(String status, long count, long totalAmountRequested) {
    this.status = status;
    this.count = count;
    this.totalAmountRequested = totalAmountRequested;
  }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public long getCount() { return count; }
  public void setCount(long count) { this.count = count; }

  public long getTotalAmountRequested() { return totalAmountRequested; }
  public void setTotalAmountRequested(long totalAmountRequested) { this.totalAmountRequested = totalAmountRequested; }
}
