package com.server.services;

import java.util.List;

import com.server.domain.Order;
import com.server.domain.User;

public interface OrderService {
    Order createOrder(User user);

    List<Order> getOrderByUser(User user);
}