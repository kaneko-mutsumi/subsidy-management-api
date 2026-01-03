package com.example.subsidy_management_api.api;

import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateRequest;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationCreateResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationDetailResponse;
import com.example.subsidy_management_api.api.dto.SubsidyApplicationListItem;
import com.example.subsidy_management_api.service.SubsidyApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
      @Valid @RequestBody SubsidyApplicationCreateRequest request) {
    return service.create(request);
  }

  @GetMapping
  public List<SubsidyApplicationListItem> list(
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) String q
  ) {
    return service.findList(status, from, to, q);
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