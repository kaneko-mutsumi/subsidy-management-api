package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.ApiErrorResponse;
import com.example.subsidy_management_api.exception.NotFoundException;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleNotFound(NotFoundException ex) {
    return new ApiErrorResponse("NOT_FOUND", ex.getMessage(), OffsetDateTime.now());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleValidation(Exception ex) {
    return new ApiErrorResponse("BAD_REQUEST", "Validation failed", OffsetDateTime.now());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleIllegalArgument(IllegalArgumentException ex) {
    return new ApiErrorResponse("BAD_REQUEST",ex.getMessage(),java.time.OffsetDateTime.now()
    );
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleNotReadable(HttpMessageNotReadableException ex) {
    return new ApiErrorResponse("BAD_REQUEST", "Invalid request body", OffsetDateTime.now());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handleUnexpected(Exception ex) {
    return new ApiErrorResponse("INTERNAL_ERROR", "Unexpected error", OffsetDateTime.now());
  }
}
