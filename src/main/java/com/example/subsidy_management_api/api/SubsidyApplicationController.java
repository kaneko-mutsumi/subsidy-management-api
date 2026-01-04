package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationDetailResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListResponse;
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
  public SubsidyApplicationCreateResponse create(
      @Valid @RequestBody SubsidyApplicationCreateRequest request
  ) {
    return service.create(request);
  }

  @GetMapping
  public SubsidyApplicationListResponse list(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) String q,
      @RequestParam(required = false) Integer limit,
      @RequestParam(required = false) Integer offset,
      @RequestParam(required = false) String sort
  ) {
    return service.findList(status, from, to, q, limit, offset, sort);
  }

  @GetMapping("/{id}")
  public SubsidyApplicationDetailResponse detail(@PathVariable long id) {
    return service.findDetail(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    service.delete(id);
  }
}