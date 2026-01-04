package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.DocumentCreateRequest;
import com.example.subsidy_management_api.api.dto.DocumentCreateResponse;
import com.example.subsidy_management_api.api.dto.DocumentListItem;
import com.example.subsidy_management_api.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

  private final DocumentService service;

  public DocumentController(DocumentService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public DocumentCreateResponse create(@Valid @RequestBody DocumentCreateRequest req) {
    return service.create(req);
  }

  @GetMapping
  public List<DocumentListItem> list(@RequestParam long applicationId) {
    return service.list(applicationId);
  }
}
