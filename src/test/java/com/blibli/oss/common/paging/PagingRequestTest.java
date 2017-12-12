package com.blibli.oss.common.paging;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Eko Kurniawan Khannedy
 */
public class PagingRequestTest {

  @Test
  public void testPaging() throws Exception {
    PagingRequest pagingRequest = new PagingRequest();
    pagingRequest.setPage(10);
    pagingRequest.setItemPerPage(5);

    Paging paging = pagingRequest.toPaging(10);
    assertEquals(Integer.valueOf(10), paging.getTotalPage());
    assertEquals(Integer.valueOf(10), paging.getPage());
    assertEquals(Integer.valueOf(5), paging.getItemPerPage());
    assertNull(paging.getTotalItem());

    paging = pagingRequest.toPaging(10, 10);
    assertEquals(Integer.valueOf(10), paging.getTotalPage());
    assertEquals(Integer.valueOf(10), paging.getPage());
    assertEquals(Integer.valueOf(5), paging.getItemPerPage());
    assertEquals(Integer.valueOf(10), paging.getTotalItem());

    PagingRequest pagingRequest1 = new PagingRequest();
    pagingRequest1.setItemPerPage(5);
    pagingRequest1.setPage(10);

    assertTrue(pagingRequest.equals(pagingRequest1));
    assertTrue(pagingRequest.equals(pagingRequest));
    assertFalse(pagingRequest.equals(null));
    assertFalse(pagingRequest.equals(paging));
    pagingRequest.hashCode();
  }
}