package com.laptopshop.laptopshop.dto.request;

import com.laptopshop.laptopshop.entity.ProductDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private BigDecimal importPrice;
    private String image;
    private int stock;
    private ProductDescription description;
    private Long brandId;
    private Set<Long> categoryId;
}

