package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.CartResponse;
import com.laptopshop.laptopshop.entity.CartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {UserMapper.class, CartDetailMapper.class})
public interface CartMapper {
    @Mapping(target = "cartDetails", ignore = true)
    CartResponse toCartResponse(CartEntity cart);
}
