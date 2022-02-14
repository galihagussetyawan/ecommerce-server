package com.server.repository;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findCartByUser(User user);

    List<Cart> findByUserAndCheckoutTrue(User user);

    List<Cart> findByUserAndOpenIsTrue(User user);
}