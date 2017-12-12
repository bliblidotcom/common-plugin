package com.blibli.oss.common.response;

import com.blibli.oss.common.error.Errors;
import com.blibli.oss.common.TestApplication;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ErrorsTest {

  @Autowired
  private Validator validator;

  @Autowired
  private MessageSource messageSource;

  @Test
  public void testError() throws Exception {
    Data data = new Data();
    MapBindingResult bindingResult = new MapBindingResult(new HashMap<>(), "data");

    validator.validate(data, bindingResult);

    if (bindingResult.hasErrors()) {
      Map<String, List<String>> errors = Errors.from(bindingResult, messageSource);

      assertTrue(errors.containsKey("firstName"));
      assertTrue(errors.containsKey("lastName"));
    } else {
      fail("It should be error");
    }
  }
}

class Data {

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}