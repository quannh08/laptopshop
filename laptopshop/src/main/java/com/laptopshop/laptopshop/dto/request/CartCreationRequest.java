package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class CartCreationRequest implements Serializable {
    private Long userId;
    private List<CartDetailCreationRequest> cartDetailRequest;
}
