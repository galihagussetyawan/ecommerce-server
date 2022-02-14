package com.server.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.server.domain.Role;
import com.server.domain.User;
import com.server.services.UserService;
import com.server.util.JwiUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class AuthResource {

        private final UserService userService;
        private final AuthenticationManager authenticationManager;
        private final JwiUtil jwiUtil;

        @PostMapping("/login")
        @CrossOrigin(origins = "http://localhost:3000")
        public ResponseEntity<?> authentication(@RequestBody AuthRequest authRequest) {

                // UsernamePasswordAuthenticationToken authenticationToken = new
                // UsernamePasswordAuthenticationToken(
                // authRequest.getUsername(), authRequest.getPassword());

                // Authentication authentication =
                // authenticationManager.authenticate(authenticationToken);
                // UserDetails user = (UserDetails) authentication.getPrincipal();

                // List<String> roles = user.getAuthorities().stream().map(item ->
                // item.getAuthority())
                // .collect(Collectors.toList());

                // // generate jwt token
                // Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                // String access_token = JWT.create().withSubject(user.getUsername())
                // .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                // .withClaim("roles",
                // user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                // .collect(Collectors.toList()))
                // .sign(algorithm);

                // String refresh_token = JWT.create().withSubject(user.getUsername())
                // .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 *
                // 1000)).sign(algorithm);

                // String username = user.getUsername();

                // Map<String, Object> response = new HashMap<>();
                // response.put("acceess_token", access_token);
                // response.put("refresh_token", refresh_token);
                // response.put("username", username);
                // response.put("roles", roles);

                // return ResponseEntity
                // .ok()
                // .body(response);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                authRequest.getUsername(), authRequest.getPassword());

                Authentication authentication = authenticationManager.authenticate(authenticationToken);
                UserDetails user = (UserDetails) authentication.getPrincipal();

                String accessToken = jwiUtil.generateToken(user);
                String refreshToken = jwiUtil.doGenerateRefreshToken(user);
                String username = jwiUtil.getUsernameFromToken(user);
                List<String> roles = jwiUtil.getRolesFromToken(user);

                Map<String, Object> response = new HashMap<>();
                response.put("username", username);
                response.put("roles", roles);
                response.put("access_token", accessToken);
                response.put("refresh_token", refreshToken);

                return ResponseEntity.ok().body(response);
        }

        @GetMapping("/refreshtoken")
        public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {

                Map<String, Object> newToken = new HashMap<>();

                try {
                        String authorizationHeader = request.getHeader(AUTHORIZATION);

                        String refreshToken = authorizationHeader.substring("Bearer ".length());

                        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                        JWTVerifier verifier = JWT.require(algorithm).build();
                        DecodedJWT decodedJWT = verifier.verify(refreshToken);

                        String username = decodedJWT.getSubject();
                        User user = userService.getUser(username);
                        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

                        String access_token = JWT.create().withSubject(user.getUsername())
                                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                                        .withIssuer(request.getRequestURL().toString())
                                        .withClaim("roles",
                                                        user.getRoles().stream().map(Role::getName)
                                                                        .collect(Collectors.toList()))
                                        .sign(algorithm);

                        newToken.put("username", user.getUsername());
                        newToken.put("roles", roles);
                        newToken.put("access_token", access_token);
                        newToken.put("refresh_token", refreshToken);

                } catch (Exception e) {

                        log.error("Error logging in: {}", e.getMessage());
                        response.setHeader("error", e.getMessage());
                }

                return ResponseEntity.ok().body(newToken);
        }
}

@Data
class AuthRequest {
        private String username;
        private String password;
}
