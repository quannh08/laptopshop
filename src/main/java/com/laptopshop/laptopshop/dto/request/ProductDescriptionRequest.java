package com.laptopshop.laptopshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescriptionRequest {
    private String hardDrive;
    private String CPUtype;
    private String ramCapacity;
    private String screenSize;
    private String operatingSystem;
}
