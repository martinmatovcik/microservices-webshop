package com.mm.learninginventoryservice.integrationtest;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mm.learninginventoryservice.model.Inventory;
import com.mm.learninginventoryservice.repository.InventoryRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerIntegrationTests {
  @Autowired private MockMvc mockMvc;
  @MockBean private InventoryRepository inventoryRepository;

  @Test
  public void isInStock_shouldReturnListOfInventoryResponseDto() throws Exception {
    List<Inventory> inventories = List.of(new Inventory(1L, "sku-test", 1));
    when(inventoryRepository.findBySkuCodeIn(List.of("sku-test"))).thenReturn(inventories);
    mockMvc
        .perform(get("/api/inventory?skuCodes=sku-test").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(1)));
  }
}
