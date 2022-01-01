package com.server.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthResource {

        private final AuthenticationManager authenticationManager;

        @PostMapping("/login")
        @CrossOrigin(origins = "http://localhost:3000")
        public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(), authRequest.getPassword());

                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                UserDetails user = (UserDetails) authentication.getPrincipal();

                List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority())
                                .collect(Collectors.toList());

                // generate jwt token
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                String access_token = JWT.create().withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                                .withClaim("roles",
                                                user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                                                .collect(Collectors.toList()))
                                .sign(algorithm);

                String refresh_token = JWT.create().withSubject(user.getUsername())
                                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).sign(algorithm);

                String username = user.getUsername();

                Map<String, Object> response = new HashMap<>();
                response.put("acceess_token", access_token);
                response.put("refresh_token", refresh_token);
                response.put("username", username);
                response.put("roles", roles);

                return ResponseEntity
                                .ok()
                                .body(response);
        }
}

@Data
class AuthRequest {
        private String username;
        private String password;
}
