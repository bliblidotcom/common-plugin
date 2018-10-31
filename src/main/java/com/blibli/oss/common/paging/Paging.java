package com.blibli.oss.common.paging;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Eko Kurniawan Khannedy
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class Paging {

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("total_page")
  private Integer totalPage;

  @JsonProperty("item_per_page")
  private Integer itemPerPage;

  @JsonProperty("total_item")
  private Integer totalItem;

  @JsonProperty("sort_by")
  private List<SortBy> sortBy;

  public Paging(Integer page, Integer totalPage, Integer itemPerPage) {
    this(page, totalPage, itemPerPage, null);
  }

  public Paging(Integer page, Integer totalPage, Integer itemPerPage, Integer totalItem) {
    this.page = page;
    this.totalPage = totalPage;
    this.itemPerPage = itemPerPage;
    this.totalItem = totalItem;
  }
}
