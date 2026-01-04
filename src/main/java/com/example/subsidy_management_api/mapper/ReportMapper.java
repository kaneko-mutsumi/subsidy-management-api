package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.api.dto.ReportStatusBreakdownItem;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

@Mapper
public interface ReportMapper {
  long countApplications(
      @Param("from") LocalDate from,
      @Param("to") LocalDate to,
      @Param("status") String status
  );

  Long sumAmountRequested(
      @Param("from") LocalDate from,
      @Param("to") LocalDate to,
      @Param("status") String status
  );

  List<ReportStatusBreakdownItem> breakdownByStatus(
      @Param("from") LocalDate from,
      @Param("to") LocalDate to
  );
}
