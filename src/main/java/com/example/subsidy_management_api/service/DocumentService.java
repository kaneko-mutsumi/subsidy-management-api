package com.example.subsidy_management_api.service;

import com.example.subsidy_management_api.api.dto.DocumentCreateRequest;
import com.example.subsidy_management_api.api.dto.DocumentCreateResponse;
import com.example.subsidy_management_api.api.dto.DocumentListItem;
import com.example.subsidy_management_api.exception.NotFoundException;
import com.example.subsidy_management_api.mapper.DocumentMapper;
import com.example.subsidy_management_api.mapper.SubsidyApplicationMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

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

    String payloadJson = null;
    if (req.getPayload() != null) {
      try {
        payloadJson = objectMapper.writeValueAsString(req.getPayload());
      } catch (JsonProcessingException e) {
        throw new IllegalArgumentException("payload is not valid JSON");
      }
    }

    documentMapper.insert(
        req.getApplicationId(),
        req.getDocumentType().name(),
        req.getDocumentNo(),
        req.getIssuedBy(),
        payloadJson
    );

    // 最短：insert後にID返却が欲しい場合は useGeneratedKeys を追加してdocumentIdを返す
    // 今回は最短で「発行できた」ことを返す（IDが必要なら次の一手で対応）
    return new DocumentCreateResponse(0L, req.getApplicationId(), req.getDocumentType().name(), OffsetDateTime.now());
  }

  @Transactional(readOnly = true)
  public List<DocumentListItem> list(long applicationId) {
    return documentMapper.findByApplicationId(applicationId);
  }
}
