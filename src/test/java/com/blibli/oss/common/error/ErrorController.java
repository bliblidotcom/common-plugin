package com.blibli.oss.common.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Eko Kurniawan Khannedy
 */
@RestControllerAdvice
public class ErrorController implements ErrorControllerHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

  @Autowired
  private MessageSource messageSource;

  @Override
  public Logger getLogger() {
    return LOGGER;
  }

  @Override
  public MessageSource getMessageSource() {
    return messageSource;
  }
}
