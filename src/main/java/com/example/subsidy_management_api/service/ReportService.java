package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.ReportSummaryResponse;
import com.example.subsidy_management_api.mapper.ReportMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ReportService {

  private final ReportMapper reportMapper;

  public ReportService(ReportMapper reportMapper) {
    this.reportMapper = reportMapper;
  }

  @Transactional(readOnly = true)
  public ReportSummaryResponse summary(String from, String to, String status) {
    LocalDate fromDate = (from == null || from.isBlank()) ? null : LocalDate.parse(from);
    LocalDate toDate = (to == null || to.isBlank()) ? null : LocalDate.parse(to);
    String st = (status == null || status.isBlank()) ? null : status;

    long count = reportMapper.countApplications(fromDate, toDate, st);
    Long sum = reportMapper.sumAmountRequested(fromDate, toDate, st);
    long total = (sum == null) ? 0L : sum;

    return new ReportSummaryResponse(fromDate, toDate, st, count, total);
  }
}
