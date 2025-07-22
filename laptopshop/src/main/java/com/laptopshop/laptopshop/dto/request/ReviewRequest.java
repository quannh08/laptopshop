package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@Builder
public class ReviewRequest implements Serializable {
    private Integer rating;
    private String comment;

    @NonNull
    private Long userId;

    @NonNull
    private Long productId;
}
