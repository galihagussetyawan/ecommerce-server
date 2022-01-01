package com.server.services.impl;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.Product;
import com.server.domain.User;
import com.server.repository.CartRepository;
import com.server.repository.ProductRepository;
import com.server.repository.UserRepository;
import com.server.services.CartService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<Cart> getCartByUser(int userId) {
        log.info("get cart by user ID : {}", userId);
        User user = userRepository.findById((long) userId).orElse(null);

        return cartRepository.findCartByUser(user);
    }

    @Override
    public Cart addToCart(int userId, int productId, int quantity) {
        log.info("userId : {}", userId);

        Product product = productRepository.findById((long) productId).get();
        User user = userRepository.findById((long) userId).get();

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .amount(product.getPrice() * quantity)
                .checkout(true)
                .build();

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateAddToCart(int id, int quantity) {
        Cart cart = cartRepository.findById((long) id).get();

        if (cart.getProduct().getStock() < 1) {
            throw new IllegalArgumentException("Out of Stock");
        } else if (quantity > cart.getProduct().getStock()) {
            throw new IllegalArgumentException("Only " + cart.getProduct().getStock() + " left in stock");
        }

        cart.setQuantity(quantity);
        cart.setAmount(cart.getProduct().getPrice() * quantity);

        return cartRepository.save(cart);
    }

    @Override
    public void delete(int id) {
        cartRepository.deleteById((long) id);
    }
}