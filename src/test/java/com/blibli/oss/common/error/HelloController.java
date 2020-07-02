package com.blibli.oss.common.error;

import com.blibli.oss.common.metadata.MetaData;
import com.blibli.oss.common.metadata.MetaDatas;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.*;
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

  @RequestMapping(value = "/validation-error", method = RequestMethod.GET)
  public Object validationError() {
    Hello hello = new Hello();
    Set<ConstraintViolation<Hello>> constraintViolations = validator.validate(hello);
    throw new ValidationException(constraintViolations);
  }

}

class Hello {

  @MetaDatas({
      @MetaData(key = "test", value = "Eko")
  })
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
