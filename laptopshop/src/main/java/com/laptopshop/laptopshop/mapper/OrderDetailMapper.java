package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.OrderDetailResponse;
import com.laptopshop.laptopshop.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetail(OrderDetail orderDetail);
}
