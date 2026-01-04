package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationDetailResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListItem;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListResponse;
import com.example.subsidy_management_api.domain.Applicant;
import com.example.subsidy_management_api.domain.SubsidyApplication;
import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.mapper.ApplicantMapper;
import com.example.subsidy_management_api.mapper.SubsidyApplicationMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubsidyApplicationService {

  private final ApplicantMapper applicantMapper;
  private final SubsidyApplicationMapper applicationMapper;

  public SubsidyApplicationService(ApplicantMapper applicantMapper,
      SubsidyApplicationMapper applicationMapper) {
    this.applicantMapper = applicantMapper;
    this.applicationMapper = applicationMapper;
  }

  @Transactional
  public SubsidyApplicationCreateResponse create(SubsidyApplicationCreateRequest request) {
    // 1) applicants
    Applicant applicant = new Applicant();
    applicant.setFullName(request.getApplicant().getFullName());
    applicant.setFullNameKana(request.getApplicant().getFullNameKana());
    applicant.setEmail(request.getApplicant().getEmail());
    applicant.setPhone(request.getApplicant().getPhone());
    applicant.setPostalCode(request.getApplicant().getPostalCode());
    applicant.setAddress1(request.getApplicant().getAddress1());
    applicant.setAddress2(request.getApplicant().getAddress2());

    applicantMapper.insert(applicant); // ← ここで applicant.id が採番される

    // 2) subsidy_applications
    SubsidyApplication app = new SubsidyApplication();
    app.setApplicantId(applicant.getId());
    app.setApplicationDate(LocalDate.parse(request.getApplication().getApplicationDate()));
    app.setAmountRequested(request.getApplication().getAmountRequested());
    app.setStatus(request.getApplication().getStatus());

    applicationMapper.insert(app); // ← ここで app.id が採番される

    return new SubsidyApplicationCreateResponse(applicant.getId(), app.getId());
  }

  @Transactional(readOnly = true)
  public SubsidyApplicationListResponse findList(
      String status,
      String from,
      String to,
      String q,
      Integer limit,
      Integer offset,
      String sort
  ) {
    LocalDate fromDate = (from == null || from.isBlank()) ? null : LocalDate.parse(from);
    LocalDate toDate = (to == null || to.isBlank()) ? null : LocalDate.parse(to);
    String keyword = (q == null || q.isBlank()) ? null : q;
    String st = (status == null || status.isBlank()) ? null : status;

    // ✅ lim/off を作る（未指定ならデフォルト）
    int lim = (limit == null) ? 20 : limit;
    int off = (offset == null) ? 0 : offset;
    if (lim <= 0) throw new IllegalArgumentException("limit must be positive");
    if (off < 0) throw new IllegalArgumentException("offset must be >= 0");

    // ✅ sort → orderBy を作る（ホワイトリスト変換）
    String sortRaw = (sort == null || sort.isBlank()) ? "applicationDate,desc" : sort;
    String orderBy = toSafeOrderBy(sortRaw);

    // ✅ count + select
    long total = applicationMapper.countList(st, fromDate, toDate, keyword);
    List<SubsidyApplicationListItem> items =
        applicationMapper.findList(st, fromDate, toDate, keyword, lim, off, orderBy);

    return new SubsidyApplicationListResponse(items, total, lim, off, sortRaw);
  }


  @Transactional(readOnly = true)
  public SubsidyApplicationDetailResponse findDetail(long id) {
    return applicationMapper.findDetailById(id)
        .orElseThrow(() -> new NotFoundException("Application not found: id=" + id));
  }

  @Transactional
  public void delete(long id) {
    int updated = applicationMapper.logicalDeleteById(id);
    if (updated == 0) {
      throw new NotFoundException("Application not found or already deleted: id=" + id);
    }
  }

  /**
   * sort（例: applicationDate,desc）を安全な ORDER BY 句の固定文字列に変換する。
   * 許可カラム：applicationDate / createdAt
   * 許可方向：asc / desc
   */
  private String toSafeOrderBy(String sort) {
    String[] parts = sort.split(",", -1);
    if (parts.length != 2) {
      throw new IllegalArgumentException(
          "sort must be like 'applicationDate,desc' or 'createdAt,asc'");
    }

    String column = parts[0].trim();
    String direction = parts[1].trim().toLowerCase();

    String sqlColumn;
    switch (column) {
      case "applicationDate":
        sqlColumn = "sa.application_date";
        break;
      case "createdAt":
        sqlColumn = "sa.created_at";
        break;
      default:
        throw new IllegalArgumentException("sort column is not allowed: " + column);
    }

    String sqlDirection;
    switch (direction) {
      case "asc":
        sqlDirection = "ASC";
        break;
      case "desc":
        sqlDirection = "DESC";
        break;
      default:
        throw new IllegalArgumentException("sort direction is invalid: " + direction);
    }

    return sqlColumn + " " + sqlDirection;
  }
}
