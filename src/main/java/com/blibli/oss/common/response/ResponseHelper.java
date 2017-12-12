package com.blibli.oss.common.response;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * @author Eko Kurniawan Khannedy
 */
public class ResponseHelper {

  /**
   * Create response ok with given data
   *
   * @param data data
   * @param <T>  data type
   * @return response ok
   */
  public static <T> Response<T> ok(T data) {
    Response<T> response = new Response<T>();
    response.setCode(HttpStatus.OK.value());
    response.setStatus(HttpStatus.OK.name());
    response.setData(data);

    return response;
  }

  /**
   * Create response ok without data
   *
   * @param <T> data type
   * @return response ok
   */
  public static <T> Response<T> ok() {
    Response<T> response = new Response<T>();
    response.setCode(HttpStatus.OK.value());
    response.setStatus(HttpStatus.OK.name());

    return response;
  }

  /**
   * Create response with status
   *
   * @param status status
   * @param <T>    data type
   * @return response status
   */
  public static <T> Response<T> status(HttpStatus status) {
    Response<T> response = new Response<T>();
    response.setCode(status.value());
    response.setStatus(status.name());

    return response;
  }

  /**
   * Create response with status and data
   *
   * @param status status
   * @param data   data
   * @param <T>    data type
   * @return response
   */
  public static <T> Response<T> status(HttpStatus status, T data) {
    Response<T> response = new Response<T>();
    response.setCode(status.value());
    response.setStatus(status.name());
    response.setData(data);

    return response;
  }

  /**
   * Create response bad request with given errors
   *
   * @param errors errors
   * @param <T>    data type
   * @return response bad request
   */
  public static <T> Response<T> badRequest(Map<String, List<String>> errors) {
    Response<T> response = new Response<T>();
    response.setCode(HttpStatus.BAD_REQUEST.value());
    response.setStatus(HttpStatus.BAD_REQUEST.name());
    response.setErrors(errors);

    return response;
  }

}
