package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.OrderDetailResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.entity.OrderDetail;
import com.laptopshop.laptopshop.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-08T12:59:22+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderDetailMapperImpl implements OrderDetailMapper {

    @Override
    public OrderDetailResponse toOrderDetail(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailResponse.builder();

        orderDetailResponse.id( orderDetail.getId() );
        orderDetailResponse.quantity( orderDetail.getQuantity() );
        orderDetailResponse.product( productEntityToProductResponse( orderDetail.getProduct() ) );
        orderDetailResponse.createdAt( orderDetail.getCreatedAt() );
        orderDetailResponse.updatedAt( orderDetail.getUpdatedAt() );

        return orderDetailResponse.build();
    }

    protected ProductResponse productEntityToProductResponse(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductResponse.ProductResponseBuilder productResponse = ProductResponse.builder();

        productResponse.id( productEntity.getId() );
        productResponse.name( productEntity.getName() );
        productResponse.price( productEntity.getPrice() );
        productResponse.importPrice( productEntity.getImportPrice() );
        productResponse.image( productEntity.getImage() );
        productResponse.stock( productEntity.getStock() );
        productResponse.createdAt( productEntity.getCreatedAt() );
        productResponse.updatedAt( productEntity.getUpdatedAt() );
        productResponse.description( productEntity.getDescription() );

        return productResponse.build();
    }
}
