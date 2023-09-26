package com.mm.learningorderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "t_order_line_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
