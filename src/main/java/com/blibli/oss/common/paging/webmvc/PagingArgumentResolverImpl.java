package com.blibli.oss.common.paging.webmvc;

import com.blibli.oss.common.paging.PagingHelper;
import com.blibli.oss.common.paging.PagingRequest;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class PagingArgumentResolverImpl implements PagingArgumentResolver {

  private PagingProperties pagingProperties;

  public PagingArgumentResolverImpl(PagingProperties pagingProperties) {
    this.pagingProperties = pagingProperties;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return PagingRequest.class.equals(parameter.getParameterType());
  }

  @Override
  public PagingRequest resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest servletRequest = (HttpServletRequest) webRequest.getNativeRequest();
    return PagingHelper.fromServlet(servletRequest, pagingProperties);
  }
}
