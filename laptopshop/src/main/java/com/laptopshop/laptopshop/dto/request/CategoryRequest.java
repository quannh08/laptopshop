package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CategoryRequest implements Serializable {
    private String name;
    private String description;
}
