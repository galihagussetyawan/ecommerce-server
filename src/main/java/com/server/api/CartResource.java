package com.server.api;

import java.util.List;

import com.server.domain.Cart;
import com.server.domain.User;
import com.server.services.CartService;
import com.server.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostAuthorize("permitAll()")
    @GetMapping("/cart/user/{id}")
    public ResponseEntity<List<Cart>> getCartByUser(@PathVariable("id") int userId) {
        User user = userService.getUserById(userId);
        List<Cart> response = cartService.getCartByUserAndOpenIsTrue(user);

        try {
            if (response.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }

    @PostMapping("/cart/save")
    public ResponseEntity<?> addToCart(@RequestBody CartRequest cartRequest) {
        Cart response = cartService.addToCart(cartRequest.getUser(), cartRequest.getProduct(),
                cartRequest.getQuantity());

        try {
            if (response == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/cart/update")
    @PostAuthorize("permitAll()")
    public ResponseEntity<?> updateAddToCart(@RequestBody UpdateCartRequest updateCartRequest) {
        Cart response = cartService.updateAddToCart(updateCartRequest.getId(), updateCartRequest.getQuantity(),
                updateCartRequest.isCheckout());

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        cartService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

@Data
class CartRequest {
    private int user;
    private int product;
    private int quantity;
}

@Data
class UpdateCartRequest {
    private int id;
    private int quantity;
    private boolean isCheckout;
}