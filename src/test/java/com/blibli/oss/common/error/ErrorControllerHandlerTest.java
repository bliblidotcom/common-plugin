package com.blibli.oss.common.error;

import com.blibli.oss.common.TestApplication;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ErrorControllerHandlerTest {

  @Value("${local.server.port}")
  private int port; //random port chosen by spring test

  @Before
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  @Test
  public void methodArgumentNotValidException() throws Exception {
    given()
        .body("{}")
        .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        .header("Accept", MediaType.ALL_VALUE)
        .when()
        .post("/validate")
        .then()
        .body(containsString("400"))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void bindException() throws Exception {
    given()
        .queryParam("number", "salahformat")
        .when()
        .get("/bind")
        .then()
        .body(containsString("400"))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void noHandlerFoundException() throws Exception {
    given()
        .when()
        .get("/404")
        .then()
        .body(containsString("404"))
        .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void throwable() throws Exception {
    given()
        .when()
        .get("/exception")
        .then()
        .body(containsString("500"))
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
  }

  @Test
  public void httpRequestMethodNotSupportedException() throws Exception {
    given()
        .body("salah")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .when()
        .delete("/validate")
        .then()
        .body(containsString("405"))
        .statusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
  }

  @Test
  public void httpMessageNotReadableException() throws Exception {
    given()
        .body("salah")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .when()
        .post("/validate")
        .then()
        .body(containsString("400"))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

  @Test
  public void httpMediaTypeNotSupportedException() throws Exception {
    given()
        .body("{}")
        .header("Content-Type", "text/html")
        .when()
        .post("/validate")
        .then()
        .body(containsString("415"))
        .statusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
  }

  @Test
  public void httpMediaTypeNotAcceptableException() throws Exception {
    given()
        .body("{}")
        .header("Accept", "application/json")
        .when()
        .get("/validate")
        .then()
        .body(containsString("406"))
        .statusCode(HttpStatus.NOT_ACCEPTABLE.value());
  }

  @Test
  public void validationError() throws Exception {
    given()
        .header("Accept", "application/json")
        .when()
        .get("/validation-error")
        .then()
        .body(containsString("400"))
        .body(containsString("data"))
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

}