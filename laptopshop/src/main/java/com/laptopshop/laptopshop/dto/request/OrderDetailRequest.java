package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderDetailRequest implements Serializable {
    private Long productId;
    private int quantity;
}
