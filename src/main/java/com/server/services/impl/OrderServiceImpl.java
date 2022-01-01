package com.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.Product;
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
    public synchronized Order createOrder(User user) {
        List<Cart> carts = cartRepository.findCartByUser(user);
        List<Cart> listCart = new ArrayList<>();

        int cartTotal = 0;
        for (Cart cart : carts) {
            if (cart.isCheckout()) {
                Product product = cart.getProduct();
                product.setStock(product.getStock() - cart.getQuantity());
                cartTotal += cart.getAmount();
                listCart.add(cart);
            }
        }

        Order order = new Order();

        order.setStatus("created");
        order.setAmount(cartTotal);
        order.setCarts(listCart);

        return orderRepository.save(order);
    }
}