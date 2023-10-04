package com.mm.learninginventoryservice.service;

import com.mm.learninginventoryservice.dto.InventoryResponseDto;
import com.mm.learninginventoryservice.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
  private final InventoryRepository inventoryRepository;

  @Override
  @Transactional(readOnly = true)
  public List<InventoryResponseDto> isInStock(List<String> skuCodes) {
    return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
        .map(
            inventory ->
                InventoryResponseDto.builder()
                    .skuCode(inventory.getSkuCode())
                    .isInStock(inventory.getQuantity() > 0)
                    .build())
        .toList();
  }
}
