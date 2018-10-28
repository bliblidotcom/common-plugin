package com.blibli.oss.common;

import com.blibli.oss.common.paging.PagingInterceptor;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Eko Kurniawan Khannedy
 */
@Configuration
@EnableConfigurationProperties({PagingProperties.class})
@ConditionalOnClass(name = "org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter")
public class CommonServletAutoConfigurer extends WebMvcConfigurerAdapter {

  @Autowired
  private PagingProperties pagingProperties;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new PagingInterceptor(pagingProperties));
  }
}
