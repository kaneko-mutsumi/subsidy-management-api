package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationDetailResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListItem;
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
  public List<SubsidyApplicationListItem> findList(String status, String from, String to,
      String q) {
    LocalDate fromDate = (from == null || from.isBlank()) ? null : LocalDate.parse(from);
    LocalDate toDate = (to == null || to.isBlank()) ? null : LocalDate.parse(to);
    String keyword = (q == null || q.isBlank()) ? null : q;
    String st = (status == null || status.isBlank()) ? null : status;

    return applicationMapper.findList(st, fromDate, toDate, keyword);
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
}
