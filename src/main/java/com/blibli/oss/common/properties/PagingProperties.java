package com.blibli.oss.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Eko Kurniawan Khannedy
 */
@ConfigurationProperties("common.paging")
public class PagingProperties {

  private Integer defaultPage = 1;

  private Integer defaultItemPerPage = 50;

  private String defaultSortDirection = "asc";

  private Integer maxItemPerPage = Integer.MAX_VALUE;

  private String pageQueryParam = "page";

  private String itemPerPageQueryParam = "item_per_page";

  private String sortByQueryParam = "sort_by";

  public Integer getMaxItemPerPage() {
    return maxItemPerPage;
  }

  public void setMaxItemPerPage(Integer maxItemPerPage) {
    this.maxItemPerPage = maxItemPerPage;
  }

  public String getPageQueryParam() {
    return pageQueryParam;
  }

  public void setPageQueryParam(String pageQueryParam) {
    this.pageQueryParam = pageQueryParam;
  }

  public String getItemPerPageQueryParam() {
    return itemPerPageQueryParam;
  }

  public void setItemPerPageQueryParam(String itemPerPageQueryParam) {
    this.itemPerPageQueryParam = itemPerPageQueryParam;
  }

  public String getSortByQueryParam() {
    return sortByQueryParam;
  }

  public void setSortByQueryParam(String sortByQueryParam) {
    this.sortByQueryParam = sortByQueryParam;
  }

  public Integer getDefaultPage() {
    return defaultPage;
  }

  public void setDefaultPage(Integer defaultPage) {
    this.defaultPage = defaultPage;
  }

  public Integer getDefaultItemPerPage() {
    return defaultItemPerPage;
  }

  public void setDefaultItemPerPage(Integer defaultItemPerPage) {
    this.defaultItemPerPage = defaultItemPerPage;
  }

  public String getDefaultSortDirection() {
    return defaultSortDirection;
  }

  public void setDefaultSortDirection(String defaultSortDirection) {
    this.defaultSortDirection = defaultSortDirection;
  }
}
