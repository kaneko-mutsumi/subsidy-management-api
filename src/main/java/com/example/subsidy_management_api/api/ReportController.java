package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.ReportSummaryResponse;
import com.example.subsidy_management_api.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {

  private final ReportService service;

  public ReportController(ReportService service) {
    this.service = service;
  }

  @GetMapping("/summary")
  public ReportSummaryResponse summary(
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) String status
  ) {
    return service.summary(from, to, status);
  }
}
