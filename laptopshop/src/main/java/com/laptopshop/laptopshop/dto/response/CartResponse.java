package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CartResponse implements Serializable {
    private Long id;
    private LocalDateTime update_at;
    private int quantity;

    private UserResponse user;

    private List<CartDetailResponse> cartDetails;
}
