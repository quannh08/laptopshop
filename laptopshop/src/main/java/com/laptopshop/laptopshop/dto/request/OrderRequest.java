package com.laptopshop.laptopshop.dto.request;

import com.laptopshop.laptopshop.common.OrderStatus;
import com.laptopshop.laptopshop.common.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private String status;
    private String paymentMethod;
    private AddressRequest addressRequest;
    private Long userId;
    private List<OrderDetailRequest> orderDetails;
}
