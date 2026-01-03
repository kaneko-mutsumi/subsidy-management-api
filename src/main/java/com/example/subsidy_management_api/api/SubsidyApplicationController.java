package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.service.SubsidyApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subsidy-applications")
public class SubsidyApplicationController {

  private final SubsidyApplicationService service;

  public SubsidyApplicationController(SubsidyApplicationService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SubsidyApplicationCreateResponse create(@Valid @RequestBody SubsidyApplicationCreateRequest request) {
    return service.create(request);
  }
}
