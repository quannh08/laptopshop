package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BrandResponse implements Serializable {
    private Long id;
    private String name;

}
