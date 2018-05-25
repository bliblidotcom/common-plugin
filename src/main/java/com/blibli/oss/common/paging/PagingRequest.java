package com.blibli.oss.common.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Eko Kurniawan Khannedy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingRequest {

  private Integer page;

  private Integer itemPerPage;

  private List<SortBy> sortBy;

  /**
   * Create Paging from Paging request
   *
   * @param totalPage total page
   * @return paging
   */
  public Paging toPaging(Integer totalPage) {
    return toPaging(totalPage, null);
  }

  /**
   * Create paging from Paging request
   *
   * @param totalPage total page
   * @param totalItem total item
   * @return paging
   */
  public Paging toPaging(Integer totalPage, Integer totalItem) {
    return new Paging(page, totalPage, itemPerPage, totalItem);
  }
}
