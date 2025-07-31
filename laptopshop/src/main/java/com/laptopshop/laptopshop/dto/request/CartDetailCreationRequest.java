package com.laptopshop.laptopshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailCreationRequest implements Serializable {
    private int quantity;
    private Long productId;
}
