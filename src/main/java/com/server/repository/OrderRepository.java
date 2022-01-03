package com.server.repository;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUser(User user);
}