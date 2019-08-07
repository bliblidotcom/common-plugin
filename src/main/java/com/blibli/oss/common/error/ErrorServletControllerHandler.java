package com.blibli.oss.common.error;

import com.blibli.oss.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

public interface ErrorServletControllerHandler extends ErrorCommonControllerHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  default Response<Object> noHandlerFoundException(NoHandlerFoundException e) {
    getLogger().warn(NoHandlerFoundException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(HttpStatus.NOT_FOUND.value());
    response.setStatus(HttpStatus.NOT_FOUND.name());
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

}
