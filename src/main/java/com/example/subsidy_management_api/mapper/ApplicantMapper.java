package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.domain.Applicant;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApplicantMapper {
  int insert(Applicant applicant); // useGeneratedKeys で applicant.id に採番が入る
}
