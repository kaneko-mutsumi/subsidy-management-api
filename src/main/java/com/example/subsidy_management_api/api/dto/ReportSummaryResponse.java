package com.example.subsidy_management_api.api.dto;

import java.time.LocalDate;

public class ReportSummaryResponse {
  private LocalDate from;
  private LocalDate to;
  private String status;
  private long count;
  private long totalAmountRequested;

  public ReportSummaryResponse(LocalDate from, LocalDate to, String status, long count, long totalAmountRequested) {
    this.from = from;
    this.to = to;
    this.status = status;
    this.count = count;
    this.totalAmountRequested = totalAmountRequested;
  }

  public LocalDate getFrom() { return from; }
  public LocalDate getTo() { return to; }
  public String getStatus() { return status; }
  public long getCount() { return count; }
  public long getTotalAmountRequested() { return totalAmountRequested; }
}
