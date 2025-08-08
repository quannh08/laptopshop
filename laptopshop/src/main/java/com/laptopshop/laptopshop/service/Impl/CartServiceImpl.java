package com.laptopshop.laptopshop.service.Impl;

import com.laptopshop.laptopshop.dto.request.CartCreationRequest;
import com.laptopshop.laptopshop.dto.request.CartDetailCreationRequest;
import com.laptopshop.laptopshop.dto.response.CartResponse;
import com.laptopshop.laptopshop.entity.CartDetail;
import com.laptopshop.laptopshop.entity.CartEntity;
import com.laptopshop.laptopshop.entity.ProductEntity;
import com.laptopshop.laptopshop.entity.UserEntity;
import com.laptopshop.laptopshop.exception.ResourceNotFoundException;
import com.laptopshop.laptopshop.mapper.CartDetailMapper;
import com.laptopshop.laptopshop.mapper.CartMapper;
import com.laptopshop.laptopshop.mapper.UserMapper;
import com.laptopshop.laptopshop.repository.CartDetailRepository;
import com.laptopshop.laptopshop.repository.CartRepository;
import com.laptopshop.laptopshop.repository.ProductRepository;
import com.laptopshop.laptopshop.repository.UserRepository;
import com.laptopshop.laptopshop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final CartMapper cartMapper;

    private final CartDetailMapper cartDetailMapper;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final UserMapper userMapper;


    @Override
    public CartResponse getCartById(Long id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not exist"));



        return toCartResponse(cart);
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not existed"));

        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not exist"));

        return toCartResponse(cart);
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        CartEntity cart = cartRepository.findByUserId(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not exist by userID: "+userId));

        CartDetail cartDetail = cartDetailRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart detail not found"));

        //Update quantity
        cart.setQuantity(cart.getQuantity()- cartDetail.getQuantity());

        cartDetailRepository.delete(cartDetail);

    }

    @Override
    public CartResponse createCart(CartCreationRequest cartCreationRequest) {
        UserEntity user = userRepository.findById(cartCreationRequest.getUserId())
                .orElseThrow(()-> new ResourceNotFoundException("User not existed"));

        CartEntity cart = CartEntity.builder()
                .quantity(0)
                .user(user)
                .cartDetails(new ArrayList<>())
                .build();
        cartRepository.save(cart);

        return toCartResponse(cart);
    }

    @Override
    public CartResponse addProductToCart(Long cartId, CartDetailCreationRequest newItem) {
        CartEntity cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found with id: "+cartId));
        ProductEntity prd = productRepository.findById(newItem.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("product not found"));
        cart.setQuantity(cart.getQuantity()+ newItem.getQuantity());
        CartDetail item = CartDetail.builder()
                .quantity(newItem.getQuantity())
                .product(prd)
                .cart(cart)
                .build();

        cartDetailRepository.save(item);
        return toCartResponse(item.getCart());
    }

    @Override
    public void deleteCartItem(Long id) {
        log.info("delete cart item with id: {}", id);
        CartDetail cartDetail = cartDetailRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Item not found in your cart"));

        cartDetail.getCart().setQuantity(cartDetail.getCart().getQuantity()-cartDetail.getQuantity());

        cartDetailRepository.delete(cartDetail);
        log.info("Cart deleted item with id: "+id);
    }

    public CartResponse toCartResponse(CartEntity cart){
        CartResponse cartResponse = cartMapper.toCartResponse(cart);

        cartResponse.setCartDetails(cart.getCartDetails().stream().map(cartDetailMapper::toCartDetail).toList());

        return cartResponse;
    }
}
