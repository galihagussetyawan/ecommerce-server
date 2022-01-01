package com.server.services;

import java.util.List;

import com.server.domain.Cart;

public interface CartService {
    List<Cart> getCartByUser(int userId);

    Cart addToCart(int userId, int productId, int quantity);

    Cart updateAddToCart(int id, int quantity);

    void delete(int id);
}
