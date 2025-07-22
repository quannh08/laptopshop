package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.CartDetailResponse;
import com.laptopshop.laptopshop.entity.CartDetail;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T14:08:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CartDetailMapperImpl implements CartDetailMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public CartDetailResponse toCartDetail(CartDetail cartDetail) {
        if ( cartDetail == null ) {
            return null;
        }

        CartDetailResponse.CartDetailResponseBuilder cartDetailResponse = CartDetailResponse.builder();

        cartDetailResponse.id( cartDetail.getId() );
        cartDetailResponse.quantity( cartDetail.getQuantity() );
        cartDetailResponse.product( productMapper.toProductResponse( cartDetail.getProduct() ) );

        return cartDetailResponse.build();
    }
}
