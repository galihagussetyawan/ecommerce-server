package com.server.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import com.server.domain.BillingAddress;
import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.Payment;
import com.server.domain.Product;
import com.server.domain.ShippingAddress;
import com.server.domain.User;
import com.server.repository.CartRepository;
import com.server.repository.OrderRepository;
import com.server.services.OrderService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public synchronized Order createOrder(User user, List<Cart> carts, ShippingAddress shippingAddress) {

        List<Cart> listCart = new ArrayList<>();

        if (shippingAddress.getShippingAddressStreet1().isEmpty()
                || shippingAddress.getShippingAddressCity().isEmpty()
                || shippingAddress.getShippingAddressState().isEmpty()
                || shippingAddress.getShippingAddressCountry().isEmpty()
                || shippingAddress.getShippingAddressZipCode().isEmpty()) {

            throw new IllegalArgumentException("shipping address must be complete");
        }

        int cartTotal = 0;
        for (Cart cart : carts) {
            Product product = cart.getProduct();
            product.setStock(product.getStock() - cart.getQuantity());
            cartTotal += cart.getAmount();
            listCart.add(cart);
        }

        Order order = Order.builder()
                .shippingAddress(shippingAddress)
                .orderDate(Calendar.getInstance().getTime())
                .status("created")
                .amount(cartTotal)
                .carts(listCart)
                .user(user)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        return orderRepository.findOrderByUser(user);
    }
}