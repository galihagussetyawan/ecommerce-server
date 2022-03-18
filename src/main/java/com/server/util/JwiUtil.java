package com.server.util;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwiUtil {

    private final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public String generateToken(UserDetails user) {

        return doGenerateToken(user);
    }

    public String doGenerateToken(UserDetails user) {

        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withClaim("roles",
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        // 30 * 60 * 1000
    }

    public String doGenerateRefreshToken(UserDetails user) {

        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
                .sign(algorithm);

        // 10 * 60 * 60 * 1000
    }

    public String getUsernameFromToken(UserDetails user) {
        return user.getUsername();
    }

    public List<String> getRolesFromToken(UserDetails user) {
        List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return roles;
    }

}
