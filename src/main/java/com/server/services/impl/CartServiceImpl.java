package com.server.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Cart> getCartByUser(User user) {

        log.info("GET CART BY USER: " + user.getUsername());
        return cartRepository.findCartByUser(user);
    }

    @Override
    public List<Cart> getCartByUserAndCheckoutIsTrue(User user) {

        try {
            return cartRepository.findByUserAndCheckoutTrue(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Cart> getCartByUserAndOpenIsTrue(User user) {
        return cartRepository.findByUserAndOpenIsTrue(user);
    }

    @Override
    public Cart addToCart(String username, int productId, int quantity) {

        try {

            Product product = productRepository.findById((long) productId).get();
            User user = userRepository.findByUsername(username);

            if (product.getCreatedBy().equals(user.getUsername())) {
                throw new RuntimeException("Produk tidak boleh dibeli sendiri");
            }

            if (quantity > product.getStock()) {
                throw new RuntimeException("Stock product only " + product.getStock());
            }

            Cart cart = Cart.builder()
                    .user(user)
                    .product(product)
                    .quantity(quantity)
                    .amount(product.getPrice() * quantity)
                    .checkout(true)
                    .open(true)
                    .build();

            log.info("Successfully added PRODUCT with ID:" + product.getId() + " to USER with ID:" + user.getId());
            return cartRepository.save(cart);

        } catch (Exception e) {

            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Cart updateAddToCart(int id, int quantity, boolean checkout) {

        log.info("UPDATE CART BY ID " + id);

        Cart cart = cartRepository.findById((long) id).get();

        if (cart.getProduct().getStock() < 1) {
            throw new IllegalArgumentException("Out of Stock");

        } else if (quantity > cart.getProduct().getStock()) {
            throw new IllegalArgumentException("Only " + cart.getProduct().getStock() + "left in stock");
        }

        cart.setCheckout(checkout);
        cart.setQuantity(quantity);
        cart.setAmount(cart.getProduct().getPrice() * quantity);

        Map<String, Object> response = new HashMap<>();
        response.put("id", cart.getId());
        response.put("quantity", cart.getQuantity());
        response.put("checkout", cart.isCheckout());
        response.put("opne", cart.isOpen());
        response.put("product", cart.getProduct());

        log.info("CART CHANGE " + response);

        return cartRepository.save(cart);
    }

    @Override
    public void deleteById(int id) {
        log.info("DELETE CART ID: " + id);

        cartRepository.deleteById((long) id);
    }

    @Override
    public void clearCart(List<Cart> carts) {

        for (Cart cartItem : carts) {
            cartItem.setOpen(false);
        }
    }
}