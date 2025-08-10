package com.laptopshop.laptopshop.dto.response;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderPageResponse extends PageResponseAbstract implements Serializable {
    private List<OrderResponse> orderResponses;
}
