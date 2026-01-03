package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.domain.Applicant;
import com.example.subsidy_management_api.domain.SubsidyApplication;
import com.example.subsidy_management_api.mapper.ApplicantMapper;
import com.example.subsidy_management_api.mapper.SubsidyApplicationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SubsidyApplicationService {

  private final ApplicantMapper applicantMapper;
  private final SubsidyApplicationMapper applicationMapper;

  public SubsidyApplicationService(ApplicantMapper applicantMapper, SubsidyApplicationMapper applicationMapper) {
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
}
