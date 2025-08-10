package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.CartDetailResponse;
import com.laptopshop.laptopshop.entity.CartDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface CartDetailMapper {
    CartDetailResponse toCartDetail(CartDetail cartDetail);
}
