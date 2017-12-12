package com.blibli.oss.common.paging;

import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eko Kurniawan Khannedy
 */
public interface PagingController {

  /**
   * Get Paging from servlet paging
   *
   * @param servletRequest servlet request
   * @return paging or null
   */
  @ModelAttribute
  default PagingRequest getPaging(HttpServletRequest servletRequest) {
    return (PagingRequest) servletRequest.getAttribute(PagingConstant.ATTRIBUTE);
  }

}
