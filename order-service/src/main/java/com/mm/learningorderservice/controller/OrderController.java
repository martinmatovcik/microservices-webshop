package com.mm.learningorderservice.controller;

import com.mm.learningorderservice.dto.OrderRequestDto;
import com.mm.learningorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
    String orderNumber = orderService.placeOrder(orderRequestDto);
    return "Order placed successfully. Number of your order is: " + orderNumber;
  }
}
