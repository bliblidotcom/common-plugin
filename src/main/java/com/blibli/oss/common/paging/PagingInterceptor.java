package com.blibli.oss.common.paging;

import com.blibli.oss.common.properties.PagingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    return paging;
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
