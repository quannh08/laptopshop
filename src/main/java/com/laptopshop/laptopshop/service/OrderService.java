package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.OrderRequest;
import com.laptopshop.laptopshop.dto.response.OrderPageResponse;
import com.laptopshop.laptopshop.dto.response.OrderResponse;
import com.laptopshop.laptopshop.dto.response.ProductPageResponse;

public interface OrderService {

    OrderPageResponse getAllOrder(int page, int size);

    OrderPageResponse findOrderByUserId(Long userId, int page,int size);

    OrderResponse findById(Long orderId);

    long save(OrderRequest request);

    OrderResponse update(Long id, OrderRequest request);

    void delete(Long id);
}
