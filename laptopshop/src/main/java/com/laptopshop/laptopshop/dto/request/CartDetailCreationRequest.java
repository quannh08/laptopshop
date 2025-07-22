package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CartDetailCreationRequest implements Serializable {
    private int quantity;
    private Long productId;
}
