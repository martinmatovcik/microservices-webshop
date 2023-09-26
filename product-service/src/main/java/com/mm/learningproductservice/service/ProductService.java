package com.mm.learningproductservice.service;

import com.mm.learningproductservice.dto.ProductRequestDto;
import com.mm.learningproductservice.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequestDto productRequestDto);

    List<ProductResponseDto> getAllProducts();
}
