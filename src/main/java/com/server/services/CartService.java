package com.server.services;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.User;

public interface CartService {
    List<Cart> getCartByUser(int userId);

    List<Cart> getCartByUserAndCheckoutIsTrue(User user);

    List<Cart> getCartByUserAndOpenIsTrue(User user);

    Cart addToCart(int userId, int productId, int quantity);

    Cart updateAddToCart(int id, int quantity, boolean isCheckout);

    void deleteById(int id);

    void clearCart(List<Cart> carts);
}