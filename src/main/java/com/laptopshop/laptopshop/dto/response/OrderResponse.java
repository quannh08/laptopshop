package com.laptopshop.laptopshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laptopshop.laptopshop.common.OrderStatus;
import com.laptopshop.laptopshop.common.PaymentMethod;
import com.laptopshop.laptopshop.entity.AddressEntity;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponse implements Serializable {
    private Long id;
    private BigDecimal totalPrice;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime updatedAt;

    private String paymentMethod;
    private AddressEntity address;
    private UserResponse userResponse;
    private List<OrderDetailResponse> orderDetails;
}
