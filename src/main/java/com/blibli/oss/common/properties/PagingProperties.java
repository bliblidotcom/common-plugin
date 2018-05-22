package com.blibli.oss.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Eko Kurniawan Khannedy
 */
@ConfigurationProperties("common.paging")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagingProperties {

  private Integer defaultPage = 1;

  private Integer defaultItemPerPage = 50;

  private String defaultSortDirection = "asc";

  private Integer maxItemPerPage = Integer.MAX_VALUE;

  private String pageQueryParam = "page";

  private String itemPerPageQueryParam = "item_per_page";

  private String sortByQueryParam = "sort_by";
}
