package com.laptopshop.laptopshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime updatedAt;

    private ProductDescription description;

    private BrandResponse brandResponse;

    private Set<CategoryResponse> categoryResponses;
}
