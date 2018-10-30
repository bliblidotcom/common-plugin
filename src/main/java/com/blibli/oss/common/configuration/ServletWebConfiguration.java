package com.blibli.oss.common.configuration;

import com.blibli.oss.common.paging.PagingInterceptor;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

public class ServletWebConfiguration extends WebMvcConfigurerAdapter {

  private PagingProperties pagingProperties;

  public ServletWebConfiguration(PagingProperties pagingProperties) {
    this.pagingProperties = pagingProperties;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new PagingInterceptor(pagingProperties));
  }
}
