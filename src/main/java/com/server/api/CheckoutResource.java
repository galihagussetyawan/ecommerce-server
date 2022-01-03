package com.server.api;

import com.server.domain.Order;
import com.server.domain.User;
import com.server.repository.OrderRepository;
import com.server.repository.UserRepository;
import com.server.services.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutResource {

    private final UserRepository userRepository;
    private final OrderService orderService;

    @PostMapping("/checkout/{id}")
    public ResponseEntity<Order> checkout(@PathVariable("id") int id) {

        User user = userRepository.findById((long) id).orElse(null);

        Order order = orderService.createOrder(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderByUser(@PathVariable("id") int id) {
        User user = userRepository.findById((long) id).orElse(null);

        return ResponseEntity.ok().body(orderService.getOrderByUser(user));
    }
}