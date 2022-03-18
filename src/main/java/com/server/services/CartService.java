package com.server.services;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.User;

public interface CartService {
    List<Cart> getCartByUser(User user);

    List<Cart> getCartByUserAndCheckoutIsTrue(User user);

    List<Cart> getCartByUserAndOpenIsTrue(User user);

    Cart addToCart(String username, int productId, int quantity);

    Cart updateAddToCart(int id, int quantity, boolean checkout);

    void deleteById(int id);

    void clearCart(List<Cart> carts);
}