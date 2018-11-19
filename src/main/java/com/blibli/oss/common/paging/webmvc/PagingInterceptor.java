package com.blibli.oss.common.paging.webmvc;

import com.blibli.oss.common.paging.PagingConstant;
import com.blibli.oss.common.paging.PagingHelper;
import com.blibli.oss.common.paging.PagingRequest;
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
 * @deprecated we don't use Interceptor anymore to inject PagingRequest
 */
@Deprecated
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
        PagingRequest paging = PagingHelper.fromServlet(request, pagingProperties);
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

}
