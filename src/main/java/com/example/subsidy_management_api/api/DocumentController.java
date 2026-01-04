package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.DocumentCreateRequest;
import com.example.subsidy_management_api.api.dto.DocumentCreateResponse;
import com.example.subsidy_management_api.api.dto.DocumentDraftResponse;
import com.example.subsidy_management_api.api.dto.DocumentIssueRequest;
import com.example.subsidy_management_api.api.dto.DocumentIssueResponse;
import com.example.subsidy_management_api.api.dto.DocumentListItem;
import com.example.subsidy_management_api.domain.DocumentType;
import com.example.subsidy_management_api.service.DocumentDraftService;
import com.example.subsidy_management_api.service.DocumentIssueService;
import com.example.subsidy_management_api.service.DocumentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentController {

  private final DocumentService service;
  private final DocumentDraftService draftService;
  private final DocumentIssueService issueService;

  public DocumentController(DocumentService service, DocumentDraftService draftService, DocumentIssueService issueService) {
    this.service = service;
    this.draftService = draftService;
    this.issueService = issueService;
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

  @GetMapping("/draft")
  public DocumentDraftResponse draft(
      @RequestParam long applicationId,
      @RequestParam DocumentType documentType
  ) {
    return draftService.draft(applicationId, documentType);
  }

  @PostMapping("/issue")
  @ResponseStatus(HttpStatus.CREATED)
  public DocumentIssueResponse issue(@Valid @RequestBody DocumentIssueRequest req) {
    return issueService.issue(req);
  }
}
