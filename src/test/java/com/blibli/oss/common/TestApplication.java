package com.blibli.oss.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Eko Kurniawan Khannedy
 */
@SpringBootApplication(
  exclude = CommonWebFluxAutoConfigurer.class
)
@Import(CommonServletAutoConfigurer.class)
public class TestApplication {
}
