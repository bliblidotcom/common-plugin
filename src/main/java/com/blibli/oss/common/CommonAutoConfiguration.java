package com.blibli.oss.common;

import com.blibli.oss.common.properties.PagingProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
  PagingProperties.class
})
public class CommonAutoConfiguration {

}
