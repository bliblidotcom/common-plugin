package com.blibli.oss.common;

import com.blibli.oss.common.configuration.ServletWebConfiguration;
import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Eko Kurniawan Khannedy
 */
@Configuration
@EnableConfigurationProperties({PagingProperties.class})
public class CommonAutoConfigurer {

  @Bean
  @ConditionalOnClass(WebMvcConfigurerAdapter.class)
  public ServletWebConfiguration servletWebConfiguration(PagingProperties pagingProperties) {
    return new ServletWebConfiguration(pagingProperties);
  }

}
