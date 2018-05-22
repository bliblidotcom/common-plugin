package com.blibli.oss.common.paging;

import com.blibli.oss.common.properties.PagingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Eko Kurniawan Khannedy
 */
public class PagingInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(PagingInterceptor.class);

  private PagingProperties pagingProperties;

  public PagingInterceptor(PagingProperties pagingProperties) {
    this.pagingProperties = pagingProperties;
  }

  @Override
  public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      if (containsSessionParam(method.getMethodParameters())) {
        PagingRequest paging = fromServlet(request);
        request.setAttribute(PagingConstant.ATTRIBUTE, paging);
      }
    }
    return super.preHandle(request, response, handler);
  }

  /**
   * Check is parameters contains Session parameter
   *
   * @param parameters parameters
   * @return true if contains, false if not
   */
  private boolean containsSessionParam(MethodParameter[] parameters) {
    for (MethodParameter parameter : parameters) {
      if (parameter.getParameterType() == PagingRequest.class) {
        return true;
      }
    }
    return false;
  }

  /**
   * Get Paging from servlet, or it will create default paging request
   *
   * @param request servlet request
   * @return paging request
   */
  public PagingRequest fromServlet(HttpServletRequest request) {
    PagingRequest paging = new PagingRequest();

    String page = request.getParameter(pagingProperties.getPageQueryParam());
    if (page == null) {
      paging.setPage(pagingProperties.getDefaultPage());
    } else {
      paging.setPage(toInt(page, pagingProperties.getDefaultPage()));
    }

    String itemPerPage = request.getParameter(pagingProperties.getItemPerPageQueryParam());
    if (itemPerPage == null) {
      paging.setItemPerPage(pagingProperties.getDefaultItemPerPage());
    } else {
      paging.setItemPerPage(toInt(itemPerPage, pagingProperties.getDefaultItemPerPage()));
    }

    if (paging.getItemPerPage() > pagingProperties.getMaxItemPerPage()) {
      paging.setItemPerPage(pagingProperties.getMaxItemPerPage());
    }

    String sortByFromRequest = request.getParameter(pagingProperties.getSortByQueryParam());
    if (StringUtils.isEmpty(sortByFromRequest)) {
      paging.setSortBy(Collections.emptyList());
    } else {
      paging.setSortBy(toSortByList(sortByFromRequest));
    }

    return paging;
  }

  /**
   * Transform sort from request to list of sort by.
   *
   * @param sortFromRequest contains multiple sortFromRequest by separated by comma (,)
   *                        with each item contains property name and sorting direction separated by semi-colon (:)
   * @return list of sort by
   */
  public List<SortBy> toSortByList(String sortFromRequest) {
    return Arrays.stream(sortFromRequest.split(","))
        .map(this::toSortBy)
        .filter(Objects::nonNull)
        .filter(sortBy -> Objects.nonNull(sortBy.getPropertyName()))
        .collect(Collectors.toList());
  }

  /**
   * Get sort by from sort from request by splitting the sort from request with semi-colon (:).
   * If sort from request is blank or contain only semi-colon (:), then null is returned.
   *
   * @param sortFromRequest containing property name and sorting direction separated by semi-colon (:)
   * @return sort by
   */
  private SortBy toSortBy(String sortFromRequest) {
    sortFromRequest = sortFromRequest.trim();
    if (StringUtils.isEmpty(sortFromRequest.replaceAll(":", "")) || sortFromRequest.startsWith(":")) {
      return null;
    }

    String[] sortBy = sortFromRequest.split(":");

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
  private String getAt(String[] strings, int index, String defaultValue) {
    return strings.length <= index ? defaultValue : strings[index];
  }

  /**
   * Parse string to integer, if error, it will return default value
   *
   * @param value        string value
   * @param defaultValue default value
   * @return parse integer or default value
   */
  public Integer toInt(String value, Integer defaultValue) {
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException ex) {
      return defaultValue;
    }
  }
}
