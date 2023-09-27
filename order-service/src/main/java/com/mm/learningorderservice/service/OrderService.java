package com.mm.learningorderservice.service;

import com.mm.learningorderservice.dto.OrderRequestDto;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequestDto orderRequestDto);
}
