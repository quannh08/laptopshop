package com.laptopshop.laptopshop.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    private String addressLine;
    private String ward;
    private String city;
    private String fullName;
}
