package com.blibli.oss.common.error;

import com.blibli.oss.common.response.Response;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ErrorCommonControllerHandler {

  Logger getLogger();

  MessageSource getMessageSource();

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  default Response<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
    getLogger().warn(MethodArgumentNotValidException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setStatus(HttpStatus.BAD_REQUEST.name());
    response.setErrors(Errors.from(e.getBindingResult(), getMessageSource()));
    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  default Response<Object> bindException(BindException e) {
    getLogger().warn(BindException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setStatus(HttpStatus.BAD_REQUEST.name());
    response.setErrors(Errors.from(e.getBindingResult(), getMessageSource()));
    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  default Response<Object> validationException(ValidationException e) {
    getLogger().warn(ValidationException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setStatus(HttpStatus.BAD_REQUEST.name());
    response.setErrors(Errors.from(e.getConstraintViolations()));
    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  default Response<Object> httpMessageNotReadableException(HttpMessageNotReadableException e) {
    getLogger().warn(HttpMessageNotReadableException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setStatus(HttpStatus.BAD_REQUEST.name());
    return response;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  default Response<Object> throwable(Throwable e) {
    getLogger().error(e.getClass().getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
    return response;
  }
}
