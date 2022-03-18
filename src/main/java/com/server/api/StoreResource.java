package com.server.api;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import com.server.domain.Store;
import com.server.domain.User;
import com.server.services.StoreService;
import com.server.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StoreResource {

    private final UserService userService;
    private final StoreService storeService;

    @GetMapping("/store")
    @PostAuthorize("hasAnyAuthority('BUYER', 'SELLER')")
    public ResponseEntity<?> getStore(Principal principal) {

        try {
            User user = userService.getUser(principal.getName());
            Store store = storeService.getStoreByUser(user);

            Map<String, Object> response = new HashMap<>();
            response.put("id", store.getId());
            response.put("username", store.getUsername());
            response.put("name", store.getName());

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/store/create")
    @PostAuthorize("hasAuthority('BUYER')")
    public ResponseEntity<String> createStore(@RequestBody Store storeRequest, Principal principal) {

        URI uri = URI
                .create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/create")
                        .toUriString());

        try {
            User user = userService.getUser(principal.getName());

            Store store = Store.builder()
                    .username(storeRequest.getUsername())
                    .name(storeRequest.getName())
                    .user(user)
                    .build();

            storeService.createStore(user, store);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User dengan username " + principal.getName() + " sudah memiliki toko");
        }

        return ResponseEntity.created(uri).body("Store success created");
    }
}