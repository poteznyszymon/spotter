package com.example.spotter.port.out;

import com.example.spotter.domain.User;
import io.jsonwebtoken.Claims;

import java.util.function.Function;

public interface JwtPort {
    String generateToken(User user);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token, String username);
}
