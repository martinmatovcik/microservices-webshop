package com.mm.learningproductservice.integrationTest;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mm.learningproductservice.dto.ProductRequestDto;
import com.mm.learningproductservice.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerIntegrationTests {
  @Container
  private static final MongoDBContainer mongoDBContainer =
      new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ProductRepository productRepository;

  @DynamicPropertySource
  private static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  @Order(1)
  public void getAllProducts_shouldReturnEmptyListOfProductResponseDtos() throws Exception {
    mockMvc
        .perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(0)));
  }

  @Test
  @Order(2)
  public void createProduct_shouldCreateProduct() throws Exception {
    mockMvc
        .perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getProductRequestDto())))
        .andExpect(status().isCreated());
    assertEquals(1, productRepository.findAll().size());
  }

  @Test
  @Order(3)
  public void getAllProducts_shouldReturnListOfProductResponseDtos() throws Exception {

    for (int i = 0; i < 5; i++) {
      productRepository.save(getProductRequestDto("iPhone " + (i + 1)).mapToProduct());
    }

    mockMvc
        .perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(6)))
        .andExpect(jsonPath("$[0].name", is("iPhone 15")))
        .andExpect(jsonPath("$[1].name", is("iPhone 1")))
        .andExpect(jsonPath("$[2].name", is("iPhone 2")))
        .andExpect(jsonPath("$[3].name", is("iPhone 3")))
        .andExpect(jsonPath("$[4].name", is("iPhone 4")))
        .andExpect(jsonPath("$[5].name", is("iPhone 5")));
  }

  private ProductRequestDto getProductRequestDto() {
    return ProductRequestDto.builder()
        .name("iPhone 15")
        .description("Best phone in the world!")
        .price(BigDecimal.valueOf(1200))
        .build();
  }

  private ProductRequestDto getProductRequestDto(String productName) {
    return ProductRequestDto.builder()
        .name(productName)
        .description("Best phone in the world!")
        .price(BigDecimal.valueOf(1200))
        .build();
  }
}
