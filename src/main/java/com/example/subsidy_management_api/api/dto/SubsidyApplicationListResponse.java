package com.example.subsidy_management_api.api.dto;

import java.util.List;

public class SubsidyApplicationListResponse {

  private final List<SubsidyApplicationListItem> items;
  private final long total;
  private final int limit;
  private final int offset;
  private final String sort;

  public SubsidyApplicationListResponse(
      List<SubsidyApplicationListItem> items,
      long total,
      int limit,
      int offset,
      String sort
  ) {
    this.items = items;
    this.total = total;
    this.limit = limit;
    this.offset = offset;
    this.sort = sort;
  }

  public List<SubsidyApplicationListItem> getItems() {
    return items;
  }

  public long getTotal() {
    return total;
  }

  public int getLimit() {
    return limit;
  }

  public int getOffset() {
    return offset;
  }

  public String getSort() {
    return sort;
  }
}
