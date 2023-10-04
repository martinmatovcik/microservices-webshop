package com.mm.learninginventoryservice.service;

import com.mm.learninginventoryservice.dto.InventoryResponseDto;
import java.util.List;

public interface InventoryService {
  List<InventoryResponseDto> isInStock(List<String> skuCodes);
}
