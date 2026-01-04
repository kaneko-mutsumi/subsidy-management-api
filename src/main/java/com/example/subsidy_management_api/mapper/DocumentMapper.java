package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.api.dto.DocumentListItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DocumentMapper {
  int insert(@Param("applicationId") long applicationId,
      @Param("documentType") String documentType,
      @Param("documentNo") String documentNo,
      @Param("issuedBy") String issuedBy,
      @Param("payloadJson") String payloadJson);

  List<DocumentListItem> findByApplicationId(@Param("applicationId") long applicationId);
}
