package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.DocumentIssueRequest;
import com.example.subsidy_management_api.api.dto.DocumentIssueResponse;
import com.example.subsidy_management_api.domain.DocumentPayloadTemplate;
import com.example.subsidy_management_api.domain.DocumentPayloadValidator;
import com.example.subsidy_management_api.domain.DocumentType;
import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.mapper.DocumentDraftMapper;
import com.example.subsidy_management_api.mapper.DocumentMapper;
import com.example.subsidy_management_api.mapper.param.DocumentInsertParam;
import com.example.subsidy_management_api.mapper.result.ApplicationDraftBaseRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentIssueService {

  private final DocumentDraftMapper draftMapper;
  private final DocumentMapper documentMapper;
  private final ObjectMapper objectMapper;

  public DocumentIssueService(DocumentDraftMapper draftMapper,
      DocumentMapper documentMapper,
      ObjectMapper objectMapper) {
    this.draftMapper = draftMapper;
    this.documentMapper = documentMapper;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public DocumentIssueResponse issue(DocumentIssueRequest req) {
    long applicationId = req.getApplicationId();
    DocumentType type = req.getDocumentType();

    // 1) Draftの元データ取得（申請 + 申請者）
    ApplicationDraftBaseRow row = draftMapper.findBaseByApplicationId(applicationId);
    if (row == null) {
      throw new NotFoundException("Application not found: id=" + applicationId);
    }

    // 2) テンプレ必須キーをすべて payload に含める（足りないものは null）
    List<String> requiredKeys = DocumentPayloadTemplate.requiredKeys(type);
    Map<String, Object> payload = new HashMap<>();
    for (String key : requiredKeys) {
      payload.put(key, null);
    }

    // 3) 共通自動入力（DBから）
    payload.put("applicantFullName", row.getApplicantFullName());
    payload.put("applicationDate", row.getApplicationDate());
    payload.put("amountRequested", row.getAmountRequested());

    // 4) overridesで上書き（不足分だけ渡す運用）
    Map<String, Object> overrides = req.getOverridesPayload();
    if (overrides != null) {
      for (Map.Entry<String, Object> e : overrides.entrySet()) {
        payload.put(e.getKey(), e.getValue());
      }
    }

    // 5) 必須キー検証（不足は 400 にしたいので IllegalArgumentException）
    DocumentPayloadValidator.validateRequiredKeys(type, payload);

    // 6) JSON化
    String payloadJson;
    try {
      payloadJson = objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("payload is not valid JSON");
    }

    // 7) 保存（documents）
    DocumentInsertParam param = new DocumentInsertParam();
    param.setApplicationId(applicationId);
    param.setDocumentType(type.name());
    param.setDocumentNo(req.getDocumentNo());
    param.setIssuedBy(req.getIssuedBy());
    param.setPayloadJson(payloadJson);

    documentMapper.insert(param);

    long documentId = (param.getId() == null) ? 0L : param.getId();

    return new DocumentIssueResponse(
        documentId,
        applicationId,
        type.name(),
        OffsetDateTime.now(),
        payload
    );
  }
}
