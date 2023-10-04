package com.mm.learninginventoryservice.controller;

import com.mm.learninginventoryservice.dto.InventoryResponseDto;
import com.mm.learninginventoryservice.service.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponseDto> isInStock(@RequestParam List<String> skuCodes) {
    return inventoryService.isInStock(skuCodes);
  }
}
