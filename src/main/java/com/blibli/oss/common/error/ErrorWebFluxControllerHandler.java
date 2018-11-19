package com.blibli.oss.common.error;

import com.blibli.oss.common.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ErrorWebFluxControllerHandler extends ErrorCommonControllerHandler {

  @ExceptionHandler(ServerWebInputException.class)
  default Response<Object> serverWebInputException(ServerWebInputException e) {
    getLogger().warn(ServerWebInputException.class.getName(), e);
    Map<String, List<String>> errors = new HashMap<>();
    errors.put(e.getMethodParameter().getParameterName(), Collections.singletonList(e.getReason()));

    Response<Object> response = new Response<>();
    response.setCode(e.getStatus().value());
    response.setStatus(e.getStatus().name());
    response.setErrors(errors);

    return response;
  }

  @ExceptionHandler(WebExchangeBindException.class)
  default Response<Object> serverWebInputException(WebExchangeBindException e) {
    getLogger().warn(WebExchangeBindException.class.getName(), e);
    Response<Object> response = new Response<>();
    response.setCode(e.getStatus().value());
    response.setStatus(e.getStatus().name());
    response.setErrors(Errors.from(e.getBindingResult(), getMessageSource()));
    return response;
  }

  @ExceptionHandler(ResponseStatusException.class)
  default Response<Object> responseStatusException(ResponseStatusException e) {
    getLogger().warn(ResponseStatusException.class.getName(), e);
    Map<String, List<String>> errors = new HashMap<>();
    errors.put("reason", Collections.singletonList(e.getReason()));

    Response<Object> response = new Response<>();
    response.setCode(e.getStatus().value());
    response.setStatus(e.getStatus().name());
    response.setErrors(errors);

    return response;
  }

}
