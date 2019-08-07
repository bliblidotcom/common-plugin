package com.blibli.oss.common;

import com.blibli.oss.common.paging.webflux.ReactivePagingArgumentResolver;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
@ConditionalOnClass({
  WebFluxConfigurer.class
})
public class CommonWebFluxAutoConfigurer implements WebFluxConfigurer {

  @Autowired
  private ReactiveAdapterRegistry reactiveAdapterRegistry;

  @Autowired
  private PagingProperties pagingProperties;

  @Override
  public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
    configurer.addCustomResolver(new ReactivePagingArgumentResolver(reactiveAdapterRegistry, pagingProperties));
  }

}
