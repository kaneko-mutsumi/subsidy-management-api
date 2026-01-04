package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.mapper.result.ApplicationDraftBaseRow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DocumentDraftMapper {
  ApplicationDraftBaseRow findBaseByApplicationId(@Param("applicationId") long applicationId);
}
