package com.example.subsidy_management_api.domain;

import java.util.List;
import java.util.Map;

public class DocumentPayloadTemplate {

  private static final Map<DocumentType, List<String>> REQUIRED_KEYS = Map.of(
      DocumentType.DECISION_NOTICE, List.of(
          "applicantFullName",
          "applicationDate",
          "amountRequested",
          "decisionDate",
          "approvedAmount"
      ),
      DocumentType.SETTLEMENT_NOTICE, List.of(
          "applicantFullName",
          "applicationDate",
          "amountRequested",
          "settlementDate",
          "approvedAmount"
      ),
      DocumentType.SURVEY_REQUEST, List.of(
          "applicantFullName",
          "applicationDate",
          "amountRequested",
          "surveyDueDate"
      )
  );

  public static List<String> requiredKeys(DocumentType type) {
    return REQUIRED_KEYS.getOrDefault(type, List.of());
  }

  private DocumentPayloadTemplate() {
    // utility class
  }
}
