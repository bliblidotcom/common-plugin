package com.blibli.oss.common.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bryan Arista
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortBy {

  private String propertyName;

  private String direction;
}
