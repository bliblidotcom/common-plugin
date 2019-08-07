package com.blibli.oss.common;

import com.blibli.oss.common.paging.webmvc.PagingArgumentResolverImpl;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Eko Kurniawan Khannedy
 */
@Configuration
@ConditionalOnClass({
  WebMvcConfigurer.class
})
public class CommonServletAutoConfigurer implements WebMvcConfigurer {

  @Autowired
  private PagingProperties pagingProperties;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new PagingArgumentResolverImpl(pagingProperties));
  }
}
