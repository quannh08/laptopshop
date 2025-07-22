package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.CartResponse;
import com.laptopshop.laptopshop.entity.CartEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T14:08:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CartResponse toCartResponse(CartEntity cart) {
        if ( cart == null ) {
            return null;
        }

        CartResponse.CartResponseBuilder cartResponse = CartResponse.builder();

        cartResponse.id( cart.getId() );
        cartResponse.update_at( cart.getUpdate_at() );
        cartResponse.quantity( cart.getQuantity() );
        cartResponse.user( userMapper.toUserResponse( cart.getUser() ) );

        return cartResponse.build();
    }
}
