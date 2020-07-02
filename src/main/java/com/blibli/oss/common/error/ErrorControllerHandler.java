package com.blibli.oss.common.error;

import com.blibli.oss.common.response.Response;
import org.slf4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;

/**
 * @author Eko Kurniawan Khannedy
 */
public interface ErrorControllerHandler {

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

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  default Response<Object> noHandlerFoundException(NoHandlerFoundException e) {
    getLogger().warn(NoHandlerFoundException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.NOT_FOUND.value());
    response.setStatus(HttpStatus.NOT_FOUND.name());
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

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  default Response<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.name());
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

  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  default Response<Object> httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
    response.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.name());
    return response;
  }

  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  default Response<Object> httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.NOT_ACCEPTABLE.value());
    response.setStatus(HttpStatus.NOT_ACCEPTABLE.name());
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
    response.setMetadata(Collections.singletonMap("errors", Errors.getMetadata(e.getConstraintViolations())));
    return response;
  }

}
