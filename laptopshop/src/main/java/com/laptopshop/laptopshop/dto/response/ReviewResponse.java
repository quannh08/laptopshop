package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Integer rating;
    private String comment;
    private Long userId;
    private Long productId;
}
