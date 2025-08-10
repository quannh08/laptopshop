package com.laptopshop.laptopshop.service;

import com.laptopshop.laptopshop.dto.request.CartCreationRequest;
import com.laptopshop.laptopshop.dto.request.CartDetailCreationRequest;
import com.laptopshop.laptopshop.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartById(Long id);

    CartResponse getCartByUserId(Long userId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    CartResponse createCart(CartCreationRequest cartCreationRequest);

    CartResponse addProductToCart(Long cartId, CartDetailCreationRequest newItem);

    void deleteCartItem(Long id);

}
