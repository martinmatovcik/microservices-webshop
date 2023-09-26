package com.mm.learningproductservice.dto;

import com.mm.learningproductservice.model.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
  private String name;
  private String description;
  private BigDecimal price;

  public Product mapToProduct() {
    return Product.builder()
        .name(this.name)
        .description(this.description)
        .price(this.price)
        .build();
  }
}
