package com.blibli.oss.common.paging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * @author Eko Kurniawan Khannedy
 */
public class Paging {

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("total_page")
  private Integer totalPage;

  @JsonProperty("item_per_page")
  private Integer itemPerPage;

  @JsonProperty("total_item")
  private Integer totalItem;

  public Paging() {
  }

  public Paging(Integer page, Integer totalPage, Integer itemPerPage) {
    this(page, totalPage, itemPerPage, null);
  }

  public Paging(Integer page, Integer totalPage, Integer itemPerPage, Integer totalItem) {
    this.page = page;
    this.totalPage = totalPage;
    this.itemPerPage = itemPerPage;
    this.totalItem = totalItem;
  }

  public Integer getTotalItem() {
    return totalItem;
  }

  public void setTotalItem(Integer totalItem) {
    this.totalItem = totalItem;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
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
    Paging paging = (Paging) o;
    return Objects.equals(page, paging.page) &&
        Objects.equals(totalPage, paging.totalPage) &&
        Objects.equals(itemPerPage, paging.itemPerPage) &&
        Objects.equals(totalItem, paging.totalItem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, totalPage, itemPerPage, totalItem);
  }
}
