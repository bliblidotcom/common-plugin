package com.blibli.oss.common.paging;

import java.util.Objects;

/**
 * @author Eko Kurniawan Khannedy
 */
public class PagingRequest {

  private Integer page;

  private Integer itemPerPage;

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

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getItemPerPage() {
    return itemPerPage;
  }

  public void setItemPerPage(Integer itemPerPage) {
    this.itemPerPage = itemPerPage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PagingRequest that = (PagingRequest) o;
    return Objects.equals(page, that.page) &&
        Objects.equals(itemPerPage, that.itemPerPage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, itemPerPage);
  }
}
