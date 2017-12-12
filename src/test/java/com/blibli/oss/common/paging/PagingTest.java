package com.blibli.oss.common.paging;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.blibli.oss.common.TestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Eko Kurniawan Khannedy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class PagingTest {

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testPagingToJson() throws Exception {
    Paging paging = new Paging(1, 10, 100, 1000);
    String json = objectMapper.writeValueAsString(paging);

    TypeReference<HashMap<String, Integer>> typeRef = new TypeReference<HashMap<String, Integer>>() {
    };
    HashMap<String, Integer> map = objectMapper.readValue(json, typeRef);

    assertEquals(Integer.valueOf(1), map.get("page"));
    assertEquals(Integer.valueOf(10), map.get("total_page"));
    assertEquals(Integer.valueOf(100), map.get("item_per_page"));
    assertEquals(Integer.valueOf(1000), map.get("total_item"));
  }

  @Test
  public void testJsonToPaging() throws Exception {
    String json = "{\n" +
        "  \"page\": 1,\n" +
        "  \"total_page\": 10,\n" +
        "  \"item_per_page\": 100,\n" +
        "  \"total_item\" : 1000\n" +
        "}";
    Paging paging = objectMapper.readValue(json, Paging.class);

    assertEquals(Integer.valueOf(1), paging.getPage());
    assertEquals(Integer.valueOf(10), paging.getTotalPage());
    assertEquals(Integer.valueOf(100), paging.getItemPerPage());
    assertEquals(Integer.valueOf(1000), paging.getTotalItem());

    Paging paging2 = new Paging(1, 10, 100, 1000);
    assertTrue(paging.equals(paging2));
    assertTrue(paging.equals(paging));
    assertFalse(paging.equals(null));
    assertFalse(paging.equals(json));
    paging.hashCode();
  }
}