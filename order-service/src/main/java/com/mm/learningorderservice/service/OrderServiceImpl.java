package com.mm.learningorderservice.service;

import com.mm.learningorderservice.dto.OrderLineItemsDto;
import com.mm.learningorderservice.dto.OrderRequestDto;
import com.mm.learningorderservice.dto.OrderResponseDto;
import com.mm.learningorderservice.model.Order;
import com.mm.learningorderservice.repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;

  @Override
  public String placeOrder(OrderRequestDto orderRequestDto) {
    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    order.setOrderLineItemsList(
        orderRequestDto.getOrderLineItemsDtoList().stream()
            .map(OrderLineItemsDto::mapToOrderLineItems)
            .toList());

    orderRepository.save(order);
    return order.getOrderNumber();
  }

  @Override
  public List<OrderResponseDto> getAllProducts() {
    return orderRepository.findAll().stream().map(this::mapToOrderResponseDto).toList();  }

  private OrderResponseDto mapToOrderResponseDto(Order order) {
    return new OrderResponseDto();
  }
}
