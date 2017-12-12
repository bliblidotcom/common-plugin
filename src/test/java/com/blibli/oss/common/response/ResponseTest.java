package com.blibli.oss.common.response;

import com.blibli.oss.common.paging.Paging;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blibli.oss.common.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class ResponseTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testFromResponseToJson() throws Exception {
    Response<String> response = new Response<>();
    response.setPaging(new Paging(1, 33, 22));
    response.setData("Eko");
    response.setCode(200);
    response.setStatus("OK");

    Map<String, List<String>> errors = new HashMap<>();
    errors.put("username", Arrays.asList("not null", "invalid"));
    response.setErrors(errors);

    String json = objectMapper.writeValueAsString(response);
    assertThat(json, containsString("1"));
    assertThat(json, containsString("33"));
    assertThat(json, containsString("22"));
    assertThat(json, containsString("200"));
    assertThat(json, containsString("Eko"));
    assertThat(json, containsString("OK"));
    assertThat(json, containsString("username"));
    assertThat(json, containsString("not null"));
    assertThat(json, containsString("invalid"));
  }

  @Test
  public void testFromJsonToResponse() throws Exception {
    String json = "{\n" +
        "  \"code\": 200,\n" +
        "  \"status\": \"OK\",\n" +
        "  \"data\": \"OK\",\n" +
        "  \"paging\": {\n" +
        "    \"page\": 1,\n" +
        "    \"item_per_page\": 25,\n" +
        "    \"total_page\": 100\n" +
        "  },\n" +
        "  \"errors\": {\n" +
        "    \"username\": [\n" +
        "      \"not null\",\n" +
        "      \"not blank\"\n" +
        "    ],\n" +
        "    \"password\": [\n" +
        "      \"not null\",\n" +
        "      \"strong password\"\n" +
        "    ]\n" +
        "  }\n" +
        "}";

    TypeReference<Response<String>> reference = new TypeReference<Response<String>>() {
    };
    Response<String> response = objectMapper.readValue(json, reference);
    assertEquals(Integer.valueOf(200), response.getCode());
    assertEquals("OK", response.getStatus());
    assertEquals("OK", response.getData());
    assertEquals(Integer.valueOf(1), response.getPaging().getPage());
    assertEquals(Integer.valueOf(25), response.getPaging().getItemPerPage());
    assertEquals(Integer.valueOf(100), response.getPaging().getTotalPage());

    assertTrue(response.getErrors().containsKey("username"));
    assertTrue(response.getErrors().containsKey("password"));

    assertThat(response.getErrors().get("username"), containsInAnyOrder("not null", "not blank"));
    assertThat(response.getErrors().get("password"), containsInAnyOrder("not null", "strong password"));
  }
}