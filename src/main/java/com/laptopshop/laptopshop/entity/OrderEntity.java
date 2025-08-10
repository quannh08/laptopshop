package com.laptopshop.laptopshop.entity;

import com.laptopshop.laptopshop.common.OrderStatus;
import com.laptopshop.laptopshop.common.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractEntity<Long> {

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Embedded
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

}
