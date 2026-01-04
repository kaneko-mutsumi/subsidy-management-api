package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.api.dto.DocumentListItem;
import com.example.subsidy_management_api.mapper.param.DocumentInsertParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DocumentMapper {

  int insert(DocumentInsertParam param);

  List<DocumentListItem> findByApplicationId(@Param("applicationId") long applicationId);
}
