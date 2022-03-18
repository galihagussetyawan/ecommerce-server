package com.server.api;

import java.security.Principal;
import java.util.List;

import com.server.domain.Cart;
import com.server.domain.Order;
import com.server.domain.ShippingAddress;
import com.server.domain.User;
import com.server.domain.UserShipping;
import com.server.services.CartService;
import com.server.services.OrderService;
import com.server.services.ShippingAddressService;
import com.server.services.UserService;
import com.server.services.UserShippingService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutResource {

    private final UserService userService;
    private final OrderService orderService;
    private final ShippingAddressService shippingAddressService;
    private final UserShippingService userShippingService;
    private final CartService cartService;

    @GetMapping("/checkout")
    // @PostAuthorize("hasAnyAuthority('BUYER', 'SELLER')")
    @PostAuthorize("hasAuthority('SELLER')")
    public ResponseEntity<?> getOrderByUser(Principal principal) {
        User user = userService.getUser(principal.getName());

        return ResponseEntity.ok().body(orderService.getOrderByUser(user));
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(Principal principal) {

        User user = userService.getUser(principal.getName());
        List<Cart> carts = cartService.getCartByUserAndCheckoutIsTrue(user);
        // UserShipping userShipping = userShippingService.getByUser(user);
        // ShippingAddress shippingAddress =
        // shippingAddressService.setByUserShipping(userShipping);

        ShippingAddress shippingAddress = (ShippingAddress) shippingAddressService
                .getByShippingAddressName(principal.getName());

        Order order = orderService.createOrder(user, carts, shippingAddress);

        cartService.clearCart(carts);

        return ResponseEntity.ok().body(order);
    }

    @PostMapping("/shipping-address")
    public ResponseEntity<ShippingAddress> addShippingAddress(@RequestBody ShippingAddress shippingAddress,
            Principal principal) {

        User user = userService.getUser(principal.getName());
        ShippingAddress response = shippingAddressService.creataShippingAddress(
                user.getName(),
                shippingAddress.getShippingAddressStreet1(),
                shippingAddress.getShippingAddressStreet2(),
                shippingAddress.getShippingAddressCity(),
                shippingAddress.getShippingAddressState(),
                shippingAddress.getShippingAddressCountry(),
                shippingAddress.getShippingAddressZipCode());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}