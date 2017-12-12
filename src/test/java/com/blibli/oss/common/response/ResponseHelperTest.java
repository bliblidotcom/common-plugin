package com.blibli.oss.common.response;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 */
public class ResponseHelperTest {

  @Test
  public void ok() throws Exception {
    Response<Object> ok = ResponseHelper.ok();
    assertEquals(Integer.valueOf(HttpStatus.OK.value()), ok.getCode());
    assertEquals(HttpStatus.OK.name(), ok.getStatus());
    assertNull(ok.getData());
  }

  @Test
  public void ok1() throws Exception {
    Response<String> ok = ResponseHelper.ok("data");
    assertEquals(Integer.valueOf(HttpStatus.OK.value()), ok.getCode());
    assertEquals(HttpStatus.OK.name(), ok.getStatus());
    assertEquals("data", ok.getData());
  }

  @Test
  public void status() throws Exception {
    Response<Object> status = ResponseHelper.status(HttpStatus.BAD_REQUEST);
    assertEquals(Integer.valueOf(HttpStatus.BAD_REQUEST.value()), status.getCode());
    assertEquals(HttpStatus.BAD_REQUEST.name(), status.getStatus());
  }

  @Test
  public void status1() throws Exception {
    Response<Object> status = ResponseHelper.status(HttpStatus.BAD_REQUEST, "data");
    assertEquals(Integer.valueOf(HttpStatus.BAD_REQUEST.value()), status.getCode());
    assertEquals(HttpStatus.BAD_REQUEST.name(), status.getStatus());
    assertEquals("data", status.getData());
  }

  @Test
  public void badRequest() throws Exception {
    Map<String, List<String>> errors = new HashMap<>();
    Response<Object> status = ResponseHelper.badRequest(errors);
    assertEquals(Integer.valueOf(HttpStatus.BAD_REQUEST.value()), status.getCode());
    assertEquals(HttpStatus.BAD_REQUEST.name(), status.getStatus());
    assertSame(errors, status.getErrors());
  }

}