package com.example.subsidy_management_api.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DocumentPayloadValidator {

  public static void validateRequiredKeys(DocumentType type, Map<String, Object> payload) {
    List<String> required = DocumentPayloadTemplate.requiredKeys(type);
    if (required.isEmpty()) {
      return;
    }

    if (payload == null) {
      throw new IllegalArgumentException("payload is required for documentType=" + type);
    }

    List<String> missing = new ArrayList<>();
    for (String key : required) {
      Object value = payload.get(key);
      if (value == null) {
        missing.add(key);
        continue;
      }
      if (value instanceof String s && s.isBlank()) {
        missing.add(key);
      }
    }

    if (!missing.isEmpty()) {
      throw new IllegalArgumentException("payload is missing required keys: " + String.join(", ", missing));
    }
  }

  private DocumentPayloadValidator() {
    // utility class
  }
}
