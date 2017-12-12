package com.blibli.oss.common.paging;

import com.blibli.oss.common.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eko Kurniawan Khannedy
 */
@RestController
public class TestPagingController implements PagingController {

  @RequestMapping("/paging/valid")
  public Response<String> validPaging(@ModelAttribute(binding = false) PagingRequest paging) {
    Response<String> response = new Response<>();
    response.setCode(HttpStatus.OK.value());
    response.setStatus(HttpStatus.OK.name());
    response.setData("OK");
    response.setPaging(paging.toPaging(100, 1000));

    return response;
  }

}
