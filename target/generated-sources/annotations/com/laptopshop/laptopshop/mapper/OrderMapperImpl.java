package com.laptopshop.laptopshop.mapper;

import com.laptopshop.laptopshop.dto.response.OrderDetailResponse;
import com.laptopshop.laptopshop.dto.response.OrderResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.dto.response.UserResponse;
import com.laptopshop.laptopshop.entity.OrderDetail;
import com.laptopshop.laptopshop.entity.OrderEntity;
import com.laptopshop.laptopshop.entity.ProductEntity;
import com.laptopshop.laptopshop.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T14:08:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderResponse toOrderResponse(OrderEntity order) {
        if ( order == null ) {
            return null;
        }

        OrderResponse.OrderResponseBuilder orderResponse = OrderResponse.builder();

        orderResponse.userResponse( userEntityToUserResponse( order.getUser() ) );
        orderResponse.id( order.getId() );
        orderResponse.totalPrice( order.getTotalPrice() );
        orderResponse.createdAt( order.getCreatedAt() );
        orderResponse.address( order.getAddress() );
        orderResponse.orderDetails( orderDetailListToOrderDetailResponseList( order.getOrderDetails() ) );

        orderResponse.paymentMethod( order.getPaymentMethod().name() );
        orderResponse.status( order.getStatus().name() );

        return orderResponse.build();
    }

    protected UserResponse userEntityToUserResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( userEntity.getId() );
        userResponse.username( userEntity.getUsername() );
        userResponse.password( userEntity.getPassword() );
        userResponse.email( userEntity.getEmail() );
        userResponse.phoneNumber( userEntity.getPhoneNumber() );
        if ( userEntity.getStatus() != null ) {
            userResponse.status( userEntity.getStatus().name() );
        }
        if ( userEntity.getRole() != null ) {
            userResponse.role( userEntity.getRole().name() );
        }

        return userResponse.build();
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
        productResponse.update_at( productEntity.getUpdate_at() );
        productResponse.description( productEntity.getDescription() );

        return productResponse.build();
    }

    protected OrderDetailResponse orderDetailToOrderDetailResponse(OrderDetail orderDetail) {
        if ( orderDetail == null ) {
            return null;
        }

        OrderDetailResponse.OrderDetailResponseBuilder orderDetailResponse = OrderDetailResponse.builder();

        orderDetailResponse.id( orderDetail.getId() );
        orderDetailResponse.quantity( orderDetail.getQuantity() );
        orderDetailResponse.product( productEntityToProductResponse( orderDetail.getProduct() ) );

        return orderDetailResponse.build();
    }

    protected List<OrderDetailResponse> orderDetailListToOrderDetailResponseList(List<OrderDetail> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderDetailResponse> list1 = new ArrayList<OrderDetailResponse>( list.size() );
        for ( OrderDetail orderDetail : list ) {
            list1.add( orderDetailToOrderDetailResponse( orderDetail ) );
        }

        return list1;
    }
}
