package com.blibli.oss.common.paging.webflux;

import com.blibli.oss.common.paging.PagingHelper;
import com.blibli.oss.common.paging.PagingRequest;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverSupport;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ReactivePagingArgumentResolver extends HandlerMethodArgumentResolverSupport implements HandlerMethodArgumentResolver {

  private PagingProperties pagingProperties;

  public ReactivePagingArgumentResolver(ReactiveAdapterRegistry adapterRegistry, PagingProperties pagingProperties) {
    super(adapterRegistry);
    this.pagingProperties = pagingProperties;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return checkParameterType(parameter, PagingRequest.class::isAssignableFrom);
  }

  @Override
  public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
    return Mono.fromSupplier(() -> PagingHelper.fromServerHttpRequest(exchange.getRequest(), pagingProperties));
  }
}
