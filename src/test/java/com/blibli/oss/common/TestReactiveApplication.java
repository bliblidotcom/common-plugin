package com.blibli.oss.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

/**
 * @author Eko Kurniawan Khannedy
 */
@SpringBootApplication(
  exclude = CommonServletAutoConfigurer.class
)
@TestPropertySource(properties = {
  "spring.main.web-application-type=reactive"
})
@Import(CommonWebFluxAutoConfigurer.class)
public class TestReactiveApplication {
}
