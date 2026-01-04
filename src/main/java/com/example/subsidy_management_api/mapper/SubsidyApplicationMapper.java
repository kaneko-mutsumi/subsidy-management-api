package com.example.subsidy_management_api.mapper;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationDetailResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListItem;
import com.example.subsidy_management_api.domain.SubsidyApplication;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SubsidyApplicationMapper {

  int insert(SubsidyApplication application); // application.id に採番が入る

  // 一覧の総件数（ページング用）
  long countList(
      @Param("status") String status,
      @Param("from") LocalDate from,
      @Param("to") LocalDate to,
      @Param("q") String q
  );

  // 一覧取得（limit/offset/orderBy）
  List<SubsidyApplicationListItem> findList(
      @Param("status") String status,
      @Param("from") LocalDate from,
      @Param("to") LocalDate to,
      @Param("q") String q,
      @Param("limit") int limit,
      @Param("offset") int offset,
      @Param("orderBy") String orderBy
  );

  Optional<SubsidyApplicationDetailResponse> findDetailById(@Param("id") long id);

  int logicalDeleteById(@Param("id") long id);

  Integer existsActiveById(long id);
}
