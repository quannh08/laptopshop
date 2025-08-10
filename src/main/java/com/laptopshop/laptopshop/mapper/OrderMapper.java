package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.OrderResponse;
import com.laptopshop.laptopshop.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userResponse", source = "user")
    @Mapping(target = "paymentMethod",expression = "java(order.getPaymentMethod().name())")
    @Mapping(target = "status", expression = "java(order.getStatus().name())")
    OrderResponse toOrderResponse(OrderEntity order);


}
