package com.laptopshop.laptopshop.dto.response;

import com.laptopshop.laptopshop.entity.ProductDescription;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal importPrice;
    private String image;
    private int stock;
    private LocalDateTime create_at;
    private LocalDateTime update_at;

    private ProductDescription description;

    private BrandResponse brandResponse;

    private Set<CategoryResponse> categoryResponses;
}
