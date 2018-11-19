package com.blibli.oss.common;

import com.blibli.oss.common.paging.webflux.ReactivePagingArgumentResolver;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;

@Configuration
@ConditionalOnClass({
  HandlerMethodArgumentResolver.class
})
public class CommonWebFluxAutoConfigurer {

  @Bean
  @Autowired
  public ReactivePagingArgumentResolver reactivePagingArgumentResolver(ReactiveAdapterRegistry reactiveAdapterRegistry,
                                                                       PagingProperties pagingProperties) {
    return new ReactivePagingArgumentResolver(reactiveAdapterRegistry, pagingProperties);
  }

}
