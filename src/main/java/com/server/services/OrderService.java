package com.server.services;

import java.util.List;

import com.server.domain.BillingAddress;
import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.Payment;
import com.server.domain.ShippingAddress;
import com.server.domain.User;

public interface OrderService {
    Order createOrder(User user, List<Cart> carts, ShippingAddress shippingAddress);

    List<Order> getOrderByUser(User user);
}