package com.mm.learningorderservice.integrationTest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mm.learningorderservice.dto.InventoryResponseDto;
import com.mm.learningorderservice.model.Order;
import com.mm.learningorderservice.repository.OrderRepository;
import java.io.IOException;
import java.util.UUID;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderControllerIntegrationTests {
  @Container
  private static final PostgreSQLContainer<?> postgresContainer =
      new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
              .withUsername("myuser")
              .withPassword("secret");

  @Autowired private MockMvc mockMvc;
  @Autowired private OrderRepository orderRepository;
  public static MockWebServer mockWebServer;
  @MockBean
  UUID mockUUID;

  @DynamicPropertySource
  private static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    dynamicPropertyRegistry.add("spring.datasource.username", postgresContainer::getUsername);
    dynamicPropertyRegistry.add("spring.datasource.password", postgresContainer::getPassword);
  }

  @BeforeAll
  static void setUp() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }
  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }


  @Test
  public void placeOrder_shouldCreateOrder() throws Exception {
//    when(mockUUID.randomUUID().toString()).thenReturn("order-random-uuid");
    InventoryResponseDto[] mockedInventoryResponseDtos = {
      new InventoryResponseDto("sku-1", true)};

mockWebServer.enqueue(new MockResponse().setBody("order-random-uuid")
        .addHeader("Content-Type", "application/json"));

    String orderJson =
        """
{
    "orderLineItemsDtoList":[
        {
            "skuCode": "sku-1",
            "price": 1200,
            "quantity": 1
        }
    ]
}
""";

    mockMvc
        .perform(
            post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
        .andExpect(status().isCreated())
            .andExpect(content().string(containsString("Order placed successfully. Number of your order is:")));

  }
}
