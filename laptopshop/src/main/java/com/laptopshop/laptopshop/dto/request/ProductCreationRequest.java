package com.laptopshop.laptopshop.dto.request;

import com.laptopshop.laptopshop.entity.ProductDescription;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ProductCreationRequest implements Serializable {
    private String name;
    private BigDecimal price;
    private BigDecimal importPrice;
    private String image;
    private int stock;
    private ProductDescription description;
    private Long brandId;
    private Set<Long> categoryId;
}
