package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandCreationRequest {
    private String name;
}
