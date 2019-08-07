package com.blibli.oss.common.paging;

import com.blibli.oss.common.TestApplication;
import com.blibli.oss.common.properties.PagingProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class PagingHelperTest {

  @Autowired
  private PagingProperties properties;

  @Test
  public void toSortByList() throws Exception {
    assertTrue(PagingHelper.toSortByList("", properties).isEmpty());
    assertTrue(PagingHelper.toSortByList(":", properties).isEmpty());
    assertTrue(PagingHelper.toSortByList(",", properties).isEmpty());

    List<SortBy> sorts = PagingHelper.toSortByList("property", properties);
    assertEquals("property", sorts.get(0).getPropertyName());
    assertEquals("asc", sorts.get(0).getDirection());

    assertTrue(PagingHelper.toSortByList(":desc", properties).isEmpty());

    sorts = PagingHelper.toSortByList("property:", properties);
    assertEquals("property", sorts.get(0).getPropertyName());
    assertEquals("asc", sorts.get(0).getDirection());

    sorts = PagingHelper.toSortByList("property:desc", properties);
    assertEquals("property", sorts.get(0).getPropertyName());
    assertEquals("desc", sorts.get(0).getDirection());
  }

  @Test
  public void toInt() throws Exception {
    assertEquals(Integer.valueOf(1), PagingHelper.toInt("1", 100));
    assertEquals(Integer.valueOf(100), PagingHelper.toInt("salah", 100));
  }

  @Test
  public void fromServletNoQueryParameter() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);

    PagingRequest pagingRequest = PagingHelper.fromServlet(request, properties);

    assertEquals(properties.getDefaultPage(), pagingRequest.getPage());
    assertEquals(properties.getDefaultItemPerPage(), pagingRequest.getItemPerPage());
    assertTrue(pagingRequest.getSortBy().isEmpty());
  }

  @Test
  public void fromServletWithQueryParameter() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(properties.getPageQueryParam())).thenReturn("10");
    when(request.getParameter(properties.getItemPerPageQueryParam())).thenReturn("100");
    when(request.getParameter(properties.getSortByQueryParam())).thenReturn("propertyName:asc");

    PagingRequest pagingRequest = PagingHelper.fromServlet(request, properties);

    assertEquals(Integer.valueOf(10), pagingRequest.getPage());
    assertEquals(Integer.valueOf(100), pagingRequest.getItemPerPage());
    assertEquals("propertyName", pagingRequest.getSortBy().get(0).getPropertyName());
    assertEquals("asc", pagingRequest.getSortBy().get(0).getDirection());
  }

  @Test
  public void fromServletWithBadQueryParameter() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(properties.getPageQueryParam())).thenReturn("salah");
    when(request.getParameter(properties.getItemPerPageQueryParam())).thenReturn("salah");
    when(request.getParameter(properties.getSortByQueryParam())).thenReturn(":");

    PagingRequest pagingRequest = PagingHelper.fromServlet(request, properties);

    assertEquals(properties.getDefaultPage(), pagingRequest.getPage());
    assertEquals(properties.getDefaultItemPerPage(), pagingRequest.getItemPerPage());
    assertTrue(pagingRequest.getSortBy().isEmpty());
  }

  @Test
  public void fromServletWithQueryParameterThatHitMaxItemPerPage() throws Exception {
    PagingProperties pagingProperties = new PagingProperties();
    BeanUtils.copyProperties(properties, pagingProperties);
    pagingProperties.setMaxItemPerPage(10);

    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter(pagingProperties.getPageQueryParam())).thenReturn("10");
    when(request.getParameter(pagingProperties.getItemPerPageQueryParam())).thenReturn("100");

    PagingRequest pagingRequest = PagingHelper.fromServlet(request, pagingProperties);

    assertEquals(Integer.valueOf(10), pagingRequest.getPage());
    assertEquals(Integer.valueOf(10), pagingRequest.getItemPerPage());
  }

}
