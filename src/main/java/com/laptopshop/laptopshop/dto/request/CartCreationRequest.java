package com.laptopshop.laptopshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartCreationRequest implements Serializable {
    private Long userId;
    private List<CartDetailCreationRequest> cartDetailRequest;
}
