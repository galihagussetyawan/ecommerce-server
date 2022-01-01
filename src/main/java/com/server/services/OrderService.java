package com.server.services;

import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.User;

public interface OrderService {
    Order createOrder(User user);
}