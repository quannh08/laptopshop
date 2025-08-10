package com.laptopshop.laptopshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends AbstractEntity<Long>{

    private int quantity;
//    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity order;
}
