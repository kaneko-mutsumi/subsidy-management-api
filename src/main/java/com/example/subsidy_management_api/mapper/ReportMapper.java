package com.example.subsidy_management_api.mapper;

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
}
