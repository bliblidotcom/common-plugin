package com.blibli.oss.common.paging;

import com.blibli.oss.common.TestApplication;
import com.blibli.oss.common.properties.PagingProperties;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PagingControllerTest {

  @Value("${local.server.port}")
  private int port; //random port chosen by spring test

  @Autowired
  private PagingProperties properties;

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  @Test
  public void testValidPaging() throws Exception {
    given()
        .queryParam(properties.getPageQueryParam(), "10")
        .queryParam(properties.getItemPerPageQueryParam(), "75")
        .when()
        .get("/paging/valid")
        .then()
        .body(containsString("10"))
        .body(containsString("75"))
        .body(containsString("100"))
        .body(containsString("1000"))
        .body(containsString("OK"))
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  public void testInvalidPaging() throws Exception {
    given()
        .queryParam(properties.getPageQueryParam(), "salah")
        .queryParam(properties.getItemPerPageQueryParam(), "salah")
        .when()
        .get("/paging/valid")
        .then()
        .body(containsString(properties.getDefaultPage().toString()))
        .body(containsString(properties.getDefaultItemPerPage().toString()))
        .body(containsString("100"))
        .body(containsString("1000"))
        .body(containsString("OK"))
        .body(not(containsString("salah")))
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  public void testNoPaging() throws Exception {
    given()
        .when()
        .get("/paging/valid")
        .then()
        .body(containsString(properties.getDefaultPage().toString()))
        .body(containsString(properties.getDefaultItemPerPage().toString()))
        .body(containsString("100"))
        .body(containsString("1000"))
        .body(containsString("OK"))
        .statusCode(HttpStatus.OK.value());
  }

}
