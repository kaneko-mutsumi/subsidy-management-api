package com.example.subsidy_management_api.api.dto;

import java.time.OffsetDateTime;

public class ApiErrorResponse {
  private String code;
  private String message;
  private OffsetDateTime timestamp;

  public ApiErrorResponse(String code, String message, OffsetDateTime timestamp) {
    this.code = code;
    this.message = message;
    this.timestamp = timestamp;
  }

  public String getCode() { return code; }
  public String getMessage() { return message; }
  public OffsetDateTime getTimestamp() { return timestamp; }
}
