package com.mm.learningproductservice.controller;

import com.mm.learningproductservice.dto.ProductRequestDto;
import com.mm.learningproductservice.dto.ProductResponseDto;
import com.mm.learningproductservice.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductRequestDto productRequestDto) {
    productService.createProduct(productRequestDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponseDto> getAllProducts() {
    return productService.getAllProducts();
  }
}
