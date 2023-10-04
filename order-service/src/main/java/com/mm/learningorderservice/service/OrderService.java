package com.mm.learningorderservice.service;

import com.mm.learningorderservice.dto.OrderRequestDto;

public interface OrderService {
  String placeOrder(OrderRequestDto orderRequestDto);
}
