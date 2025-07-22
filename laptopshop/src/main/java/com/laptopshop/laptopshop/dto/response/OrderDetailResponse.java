package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class OrderDetailResponse implements Serializable {
    private Long id;
    private int quantity;
//    private BigDecimal price;
    private ProductResponse product;
}
