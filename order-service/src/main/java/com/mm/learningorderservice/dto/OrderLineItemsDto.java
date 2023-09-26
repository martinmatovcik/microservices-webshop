package com.mm.learningorderservice.dto;

import com.mm.learningorderservice.model.OrderLineItems;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;

    public OrderLineItems mapToOrderLineItems(){
        return OrderLineItems.builder()
                .id(this.id)
                .skuCode(this.skuCode)
                .price(this.price)
                .quantity(this.quantity)
                .build();
    }
}
