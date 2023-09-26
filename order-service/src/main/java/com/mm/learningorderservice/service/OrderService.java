package com.mm.learningorderservice.service;

import com.mm.learningorderservice.dto.OrderRequestDto;
import com.mm.learningorderservice.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllProducts();
}
