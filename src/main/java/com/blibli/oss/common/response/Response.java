package com.blibli.oss.common.response;

import com.blibli.oss.common.paging.Paging;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * @author Eko Kurniawan Khannedy
 */
public class Response<T> {

  @JsonProperty("code")
  private Integer code;

  @JsonProperty("status")
  private String status;

  @JsonProperty("data")
  private T data;

  @JsonProperty("paging")
  private Paging paging;

  @JsonProperty("errors")
  private Map<String, List<String>> errors;

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }

  public Map<String, List<String>> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, List<String>> errors) {
    this.errors = errors;
  }
}
