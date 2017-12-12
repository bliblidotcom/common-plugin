package com.blibli.oss.common;

import com.blibli.oss.common.paging.PagingRequest;
import org.mockito.stubbing.Answer;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Eko Kurniawan Khannedy
 */
public class TestHelper {

  public static HandlerMethod handlerMethod() {
    MethodParameter parameter = mock(MethodParameter.class);
    when(parameter.getParameterType()).thenAnswer((Answer<Object>) invocation -> PagingRequest.class);

    HandlerMethod method = mock(HandlerMethod.class);
    when(method.getMethodParameters()).thenReturn(new MethodParameter[]{parameter});

    return method;
  }

  public static HandlerMethod handlerMethodNoPaging() {
    MethodParameter parameter = mock(MethodParameter.class);
    when(parameter.getParameterType()).thenAnswer((Answer<Object>) invocation -> Date.class);

    HandlerMethod method = mock(HandlerMethod.class);
    when(method.getMethodParameters()).thenReturn(new MethodParameter[]{parameter});

    return method;
  }

}
