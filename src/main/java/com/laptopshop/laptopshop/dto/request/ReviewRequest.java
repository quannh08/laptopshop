package com.laptopshop.laptopshop.dto.request;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest implements Serializable {
    private Integer rating;
    private String comment;

    @NonNull
    private Long userId;

    @NonNull
    private Long productId;
}
