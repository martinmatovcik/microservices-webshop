package com.mm.learningorderservice.service;

import com.mm.learningorderservice.dto.InventoryResponseDto;
import com.mm.learningorderservice.dto.OrderLineItemsDto;
import com.mm.learningorderservice.dto.OrderRequestDto;
import com.mm.learningorderservice.model.Order;
import com.mm.learningorderservice.model.OrderLineItems;
import com.mm.learningorderservice.repository.OrderRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  @Override
  public String placeOrder(OrderRequestDto orderRequestDto) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    order.setOrderLineItemsList(
        orderRequestDto.getOrderLineItemsDtoList().stream()
            .map(OrderLineItemsDto::mapToOrderLineItems)
            .toList());

    List<String> skuCodes =
        order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

    InventoryResponseDto[] inventoryResponseDtos =
        webClientBuilder.build()
            .get()
            .uri(
                "http://inventory-service/api/inventory",
                uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponseDto[].class)
            .block();

    boolean allProductsInStock = Arrays.stream(inventoryResponseDtos).allMatch(InventoryResponseDto::isInStock);

    if (allProductsInStock) {
      orderRepository.save(order);
      return order.getOrderNumber();
    } else {
      throw new IllegalArgumentException("Product is not in stock, please try again later");
    }
  }
}
