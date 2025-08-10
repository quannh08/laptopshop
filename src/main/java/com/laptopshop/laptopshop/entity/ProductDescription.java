package com.laptopshop.laptopshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductDescription {

    private String hardDrive;
    private String CPUtype;
    private String ramCapacity;
    private String screenSize;
    private String operatingSystem;
}
