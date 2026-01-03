package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.domain.SubsidyApplication;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubsidyApplicationMapper {
  int insert(SubsidyApplication application); // application.id に採番が入る
}
