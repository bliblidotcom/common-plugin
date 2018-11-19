package com.blibli.oss.common.paging;

import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PagingHelper {

  /**
   * Get Paging from Server Http Request, or it will create default paging request
   *
   * @param request          server http request
   * @param pagingProperties paging properties
   * @return paging request
   */
  public static PagingRequest fromServerHttpRequest(ServerHttpRequest request, PagingProperties pagingProperties) {
    PagingRequest paging = new PagingRequest();

    paging.setPage(getInteger(
      request.getQueryParams().getFirst(pagingProperties.getPageQueryParam()),
      pagingProperties.getDefaultPage()
    ));
    paging.setItemPerPage(getInteger(
      request.getQueryParams().getFirst(pagingProperties.getItemPerPageQueryParam()),
      pagingProperties.getDefaultItemPerPage()
    ));

    if (paging.getItemPerPage() > pagingProperties.getMaxItemPerPage()) {
      paging.setItemPerPage(pagingProperties.getMaxItemPerPage());
    }
    paging.setSortBy(getSortByList(
      request.getQueryParams().getFirst(pagingProperties.getSortByQueryParam()),
      pagingProperties
    ));

    return paging;
  }

  /**
   * Get Paging from servlet, or it will create default paging request
   *
   * @param request servlet request
   * @return paging request
   */
  public static PagingRequest fromServlet(HttpServletRequest request, PagingProperties pagingProperties) {
    PagingRequest paging = new PagingRequest();

    paging.setPage(getInteger(
      request.getParameter(pagingProperties.getPageQueryParam()),
      pagingProperties.getDefaultPage()
    ));
    paging.setItemPerPage(getInteger(
      request.getParameter(pagingProperties.getItemPerPageQueryParam()),
      pagingProperties.getDefaultItemPerPage()
    ));

    if (paging.getItemPerPage() > pagingProperties.getMaxItemPerPage()) {
      paging.setItemPerPage(pagingProperties.getMaxItemPerPage());
    }
    paging.setSortBy(getSortByList(
      request.getParameter(pagingProperties.getSortByQueryParam()),
      pagingProperties
    ));

    return paging;
  }

  /**
   * Get SortBy list from String
   *
   * @param value            String sortBy
   * @param pagingProperties paging properties
   * @return list of sort by
   */
  private static List<SortBy> getSortByList(String value, PagingProperties pagingProperties) {
    if (StringUtils.isEmpty(value)) {
      return Collections.emptyList();
    } else {
      return toSortByList(value, pagingProperties);
    }
  }

  /**
   * Get integer value from String, if failed, return default value
   *
   * @param value        string of integer
   * @param defaultValue if failed, return this value
   * @return integer
   */
  private static Integer getInteger(String value, Integer defaultValue) {
    if (value == null) {
      return defaultValue;
    } else {
      return toInt(value, defaultValue);
    }
  }

  /**
   * Transform sort from request to list of sort by.
   *
   * @param request contains multiple sort separated by comma (,) with each item contains property name
   *                and sorting direction separated by semi-colon (:)
   * @return list of sort by
   */
  public static List<SortBy> toSortByList(String request, PagingProperties pagingProperties) {
    return Arrays.stream(request.split(","))
      .map(s -> toSortBy(s, pagingProperties))
      .filter(Objects::nonNull)
      .filter(sortBy -> Objects.nonNull(sortBy.getPropertyName()))
      .collect(Collectors.toList());
  }

  /**
   * Get sort by from request by splitting the request with semi-colon (:).
   * If sort from request is blank or contain only semi-colon (:), then null is returned.
   *
   * @param request containing property name and sorting direction separated by semi-colon (:)
   * @return sort by
   */
  public static SortBy toSortBy(String request, PagingProperties pagingProperties) {
    String sort = request.trim();
    if (StringUtils.isEmpty(sort.replaceAll(":", "")) || sort.startsWith(":")) {
      return null;
    }

    String[] sortBy = sort.split(":");

    return new SortBy(
      getAt(sortBy, 0, null),
      getAt(sortBy, 1, pagingProperties.getDefaultSortDirection())
    );
  }

  /**
   * Get value of index from array. If index is out of bound from array, then return default value.
   *
   * @param strings      array of value
   * @param index        index of value
   * @param defaultValue default value
   * @return value from array or default value
   */
  public static String getAt(String[] strings, int index, String defaultValue) {
    return strings.length <= index ? defaultValue : strings[index];
  }

  /**
   * Parse string to integer, if error, it will return default value
   *
   * @param value        string value
   * @param defaultValue default value
   * @return parse integer or default value
   */
  public static Integer toInt(String value, Integer defaultValue) {
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException ex) {
      return defaultValue;
    }
  }
}
