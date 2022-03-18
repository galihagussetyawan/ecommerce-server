package com.server.api;

import java.security.Principal;
import java.util.List;

import com.server.domain.Cart;
import com.server.domain.Product;
import com.server.domain.User;
import com.server.services.CartService;
import com.server.services.ProductService;
import com.server.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CartResource {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/cart")
    // @PostAuthorize("hasAnyAuthority('BUYER', 'SELLER')")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<?> getCartByUser(Principal principal) {

        User user = userService.getUser(principal.getName());

        try {
            List<Cart> response = cartService.getCartByUserAndOpenIsTrue(user);

            return ResponseEntity
                    .ok()
                    .body(response);

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/cart/save")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest, Principal principal) {

        try {
            Cart response = cartService.addToCart(principal.getName(), cartRequest.getProduct(),
                    cartRequest.getQuantity());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/cart/update")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<?> updateAddToCart(@RequestBody UpdateCartRequest updateCartRequest) {

        Cart response = cartService.updateAddToCart(updateCartRequest.getId(), updateCartRequest.getQuantity(),
                updateCartRequest.isCheckout());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/cart")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<List<Cart>> delete(@RequestParam int id, Principal principal) {
        cartService.deleteById(id);

        User user = userService.getUser(principal.getName());
        List<Cart> cart = cartService.getCartByUser(user);

        return ResponseEntity.ok().body(cart);
    }
}

@Data
class CartRequest {
    private int product;
    private int quantity;
}

@Data
class UpdateCartRequest {
    private int id;
    private int quantity;
    private boolean checkout;
}