package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.DocumentDraftResponse;
import com.example.subsidy_management_api.domain.DocumentPayloadTemplate;
import com.example.subsidy_management_api.domain.DocumentType;
import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.mapper.DocumentDraftMapper;
import com.example.subsidy_management_api.mapper.result.ApplicationDraftBaseRow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentDraftService {

  private final DocumentDraftMapper mapper;

  public DocumentDraftService(DocumentDraftMapper mapper) {
    this.mapper = mapper;
  }

  @Transactional(readOnly = true)
  public DocumentDraftResponse draft(long applicationId, DocumentType documentType) {
    ApplicationDraftBaseRow row = mapper.findBaseByApplicationId(applicationId);
    if (row == null) {
      throw new NotFoundException("Application not found: id=" + applicationId);
    }

    // テンプレ必須キーを全て含める（足りないものは null）
    List<String> requiredKeys = DocumentPayloadTemplate.requiredKeys(documentType);
    Map<String, Object> payload = new HashMap<>();
    for (String key : requiredKeys) {
      payload.put(key, null);
    }

    // 共通の自動入力（テンプレに含まれている前提）
    payload.put("applicantFullName", row.getApplicantFullName());
    payload.put("applicationDate", row.getApplicationDate());
    payload.put("amountRequested", row.getAmountRequested());

    return new DocumentDraftResponse(applicationId, documentType.name(), payload);
  }
}
