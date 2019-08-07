package com.blibli.oss.common.error;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Collections;
import java.util.Set;

/**
 * @author Eko Kurniawan Khannedy
 */
@RestController
public class HelloController {

  @Autowired
  private Validator validator;

  @RequestMapping(value = "/validate", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Object validate(@Valid @RequestBody Hello hello) {
    return hello;
  }

  @RequestMapping(value = "/validate", method = RequestMethod.GET,
      produces = MediaType.TEXT_PLAIN_VALUE)
  public String get() {
    return "HELLO";
  }

  @RequestMapping(value = "/exception", method = RequestMethod.GET)
  public String exception() throws Throwable {
    throw new Throwable();
  }

  @RequestMapping(value = "/404", method = RequestMethod.GET)
  public String notFound() throws Throwable {
    throw new NoHandlerFoundException("GET", "/", null);
  }

  @RequestMapping(value = "/bind", method = RequestMethod.GET)
  public Object bind(@ModelAttribute Hello hello) {
    return hello;
  }

  @RequestMapping(value = "/reactive/server-web-input", method = RequestMethod.GET)
  public String serverWebInput() {
    throw new ServerWebInputException("");
  }

  @RequestMapping(value = "/reactive/web-exchange-bind", method = RequestMethod.GET)
  public String webExchangeBind() throws Exception {
    MethodParameter param = new MethodParameter(
      HelloController.class.getMethod("webExchangeBind"),
      -1
    );
    BindingResult binding = new BeanPropertyBindingResult("BliValidation", "ValidationBli");

    WebExchangeBindException exception = new WebExchangeBindException(param, binding);
    throw exception;
  }

  @RequestMapping(value = "/reactive/media-type-not-supported", method = RequestMethod.GET)
  public String mediaTypeNotAccepted() {
    throw new MediaTypeNotSupportedStatusException("BliMediaNotSupported");
  }

  @RequestMapping(value = "/reactive/not-acceptable", method = RequestMethod.GET)
  public String notAcceptable() {
    throw new NotAcceptableStatusException("NotAcceptable");
  }

  @RequestMapping(value = "/reactive/unsupported-media-type-status", method = RequestMethod.GET)
  public String unsupportedMediaType() {
    throw new UnsupportedMediaTypeStatusException("MediaStatus");
  }

  @RequestMapping(value = "/reactive/method-not-allowed", method = RequestMethod.GET)
  public String methodNotAllowed() {
    throw new MethodNotAllowedException("NotAllowed", Collections.singletonList(HttpMethod.DELETE));
  }

  @RequestMapping(value = "/reactive/server-error-exception", method = RequestMethod.GET)
  public String serverErrorException() {
    throw new ServerErrorException("Exception", new Exception());
  }

  @RequestMapping(value = "/reactive/response-status-exception", method = RequestMethod.GET)
  public String responseStatusException() {
    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

class Hello {

  @NotBlank
  private String data;

  private Integer number;

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
