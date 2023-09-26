package com.mm.learningproductservice.service;

import com.mm.learningproductservice.dto.ProductRequestDto;
import com.mm.learningproductservice.dto.ProductResponseDto;
import com.mm.learningproductservice.model.Product;
import com.mm.learningproductservice.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public void createProduct(ProductRequestDto productRequestDto) {
    Product product = productRequestDto.mapToProduct();
    productRepository.save(product);
    log.info("Product {} is saved.", product.getId());
  }

  @Override
  public List<ProductResponseDto> getAllProducts() {
    return productRepository.findAll().stream().map(this::mapToProductResponseDto).toList();
  }

  private ProductResponseDto mapToProductResponseDto(Product product) {
    return ProductResponseDto.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .build();
  }
}
