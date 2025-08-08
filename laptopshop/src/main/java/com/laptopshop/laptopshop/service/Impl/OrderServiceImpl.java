package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.common.OrderStatus;
import com.laptopshop.laptopshop.common.PaymentMethod;
import com.laptopshop.laptopshop.dto.request.OrderRequest;
import com.laptopshop.laptopshop.dto.response.OrderDetailResponse;
import com.laptopshop.laptopshop.dto.response.OrderPageResponse;
import com.laptopshop.laptopshop.dto.response.OrderResponse;
import com.laptopshop.laptopshop.dto.response.ProductResponse;
import com.laptopshop.laptopshop.entity.OrderDetail;
import com.laptopshop.laptopshop.entity.OrderEntity;
import com.laptopshop.laptopshop.entity.ProductEntity;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.AddressMapper;
import com.laptopshop.laptopshop.mapper.BrandMapper;
import com.laptopshop.laptopshop.mapper.CategoryMapper;
import com.laptopshop.laptopshop.mapper.UserMapper;
import com.laptopshop.laptopshop.repository.OrderRepository;
import com.laptopshop.laptopshop.repository.ProductRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final BrandMapper brandMapper;

    private final CategoryMapper categoryMapper;

    private final UserMapper userMapper;

    private final AddressMapper addressMapper;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    @Override
    public OrderPageResponse getAllOrder(int page, int size) {
        int pageNo=0;
        if(page>0) pageNo=page-1;

        Sort.Order sort = new Sort.Order(Sort.Direction.ASC,"id");

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(sort));

        Page<OrderEntity> entityPage = orderRepository.findAll(pageable);

        return getOrderPageResponse(pageNo,size,entityPage);
    }

    @Override
    public OrderPageResponse findOrderByUserId(Long userId, int page, int size) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not existed"));
        int pageNo=0;
        if(page>0) pageNo=page-1;

        Sort.Order sort = new Sort.Order(Sort.Direction.ASC,"id");

        Pageable pageable = PageRequest.of(pageNo,size,Sort.by(sort));
        Page<OrderEntity> entityPage = orderRepository.findByUserId(userId, pageable);
        return getOrderPageResponse(pageNo,size,entityPage);
    }

    @Override
    public OrderResponse findById(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not exist"));

        return this.toOrderResponse(order);
    }

    @Override
    public long save(OrderRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not existed"));

        List<OrderDetail> orderDetails = request.getOrderDetails().stream().map(orderdetail -> OrderDetail
                        .builder()
                        .quantity(orderdetail.getQuantity())
                        .product(productRepository.findById(orderdetail.getProductId())
                                .orElseThrow(()-> new ResourceNotFoundException("Product not found")))
                        .build()).toList();

        BigDecimal total = orderDetails.stream().map(
                orderDetail -> orderDetail.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(orderDetail.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        OrderEntity order = OrderEntity.builder()
                .status(OrderStatus.valueOf(request.getStatus()))
                .totalPrice(total)

                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .address(addressMapper.toAddress(request.getAddressRequest()))
                .user(user)
                .orderDetails(orderDetails)
                .build();
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public OrderResponse update(Long id, OrderRequest request) {

        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));

        List<OrderDetail> orderDetails = request.getOrderDetails().stream().map(orderdetail -> OrderDetail
                .builder()
                .quantity(orderdetail.getQuantity())
                .product(productRepository.findById(orderdetail.getProductId())
                        .orElseThrow(()-> new ResourceNotFoundException("Product not found")))
                .build()).toList();

        BigDecimal total = orderDetails.stream().map(
                        orderDetail -> orderDetail.getProduct().getPrice()
                                .multiply(BigDecimal.valueOf(orderDetail.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        order = OrderEntity.builder()
                .status(OrderStatus.valueOf(request.getStatus()))
                .totalPrice(total)
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .address(addressMapper.toAddress(request.getAddressRequest()))
                .user(userRepository.findById(request.getUserId())
                        .orElseThrow(()-> new ResourceNotFoundException("User not found")))
                .orderDetails(orderDetails)
                .build();


        return this.toOrderResponse(order);
    }

    @Override
    public void delete(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Order not existed with id: "+id));
        orderRepository.delete(order);
    }

    private OrderPageResponse getOrderPageResponse(int page, int size, Page<OrderEntity>orderEntities){
        log.info("Convert order page");

        List<OrderResponse> orderResponses = orderEntities.stream().map(this::toOrderResponse)
                .toList();

        OrderPageResponse orderPage = new OrderPageResponse();
        orderPage.setPageNumber(page);
        orderPage.setPageSize(size);
        orderPage.setTotalPages(orderEntities.getTotalPages());
        orderPage.setTotalElements(orderEntities.getTotalElements());
        orderPage.setOrderResponses(orderResponses);
        return orderPage;
    }

    private OrderResponse toOrderResponse(OrderEntity orderEntity){
        OrderResponse orderResponse = OrderResponse.builder()
                .id(orderEntity.getId())
                .totalPrice(orderEntity.getTotalPrice())
                .status(orderEntity.getStatus().name())
                .createdAt(orderEntity.getCreatedAt())
                .paymentMethod(orderEntity.getPaymentMethod().name())
                .address(orderEntity.getAddress())
                .userResponse(userMapper.toUserResponse(orderEntity.getUser()))
                .orderDetails(orderEntity.getOrderDetails().stream().map(this::convertToOrderDetailResponse)
                        .collect(Collectors.toList()))
                .build();
        return orderResponse;
    }

    private OrderDetailResponse convertToOrderDetailResponse(OrderDetail detail) {
        ProductEntity product = detail.getProduct();
        return OrderDetailResponse.builder()
                .id(detail.getId())
                .product(toProductResponse(detail.getProduct()))
                .quantity(detail.getQuantity())
                .build();
    }

    private ProductResponse toProductResponse(ProductEntity product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .importPrice(product.getImportPrice())
                .image(product.getImage())
                .stock(product.getStock())
                .description(product.getDescription())
                .categoryResponses(product.getCategories().stream().map(categoryMapper::toCategoryResponse)
                        .collect(Collectors.toSet()))
                .brandResponse(brandMapper.toBrandResponse(product.getBrand()))
                .build();
    }
}
