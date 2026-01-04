package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.DocumentCreateRequest;
import com.example.subsidy_management_api.api.dto.DocumentCreateResponse;
import com.example.subsidy_management_api.api.dto.DocumentListItem;
import com.example.subsidy_management_api.domain.DocumentPayloadValidator;
import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.mapper.DocumentMapper;
import com.example.subsidy_management_api.mapper.SubsidyApplicationMapper;
import com.example.subsidy_management_api.mapper.param.DocumentInsertParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {

  private final DocumentMapper documentMapper;
  private final SubsidyApplicationMapper applicationMapper;
  private final ObjectMapper objectMapper;

  public DocumentService(DocumentMapper documentMapper,
      SubsidyApplicationMapper applicationMapper,
      ObjectMapper objectMapper) {
    this.documentMapper = documentMapper;
    this.applicationMapper = applicationMapper;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public DocumentCreateResponse create(DocumentCreateRequest req) {
    Integer exists = applicationMapper.existsActiveById(req.getApplicationId());
    if (exists == null) {
      throw new NotFoundException("Application not found: id=" + req.getApplicationId());
    }

    DocumentPayloadValidator.validateRequiredKeys(req.getDocumentType(), req.getPayload());

    String payloadJson = null;
    if (req.getPayload() != null) {
      try {
        payloadJson = objectMapper.writeValueAsString(req.getPayload());
      } catch (JsonProcessingException e) {
        throw new IllegalArgumentException("payload is not valid JSON");
      }
    }

    DocumentInsertParam param = new DocumentInsertParam();
    param.setApplicationId(req.getApplicationId());
    param.setDocumentType(req.getDocumentType().name());
    param.setDocumentNo(req.getDocumentNo());
    param.setIssuedBy(req.getIssuedBy());
    param.setPayloadJson(payloadJson);

    documentMapper.insert(param);

    // MyBatisがAUTO_INCREMENTのidをparam.idにセットしてくれる
    long documentId = (param.getId() == null) ? 0L : param.getId();

    return new DocumentCreateResponse(
        documentId,
        req.getApplicationId(),
        req.getDocumentType().name(),
        OffsetDateTime.now()
    );
  }

  @Transactional(readOnly = true)
  public List<DocumentListItem> list(long applicationId) {
    return documentMapper.findByApplicationId(applicationId);
  }
}
