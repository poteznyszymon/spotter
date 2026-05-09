package com.example.spotter.adapter.out.jwt;

import com.example.spotter.domain.User;
import com.example.spotter.port.out.JwtPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public class JwtAdapter implements JwtPort {

    private final String secret;
    private final long expirationTimeMs;

    public JwtAdapter(String secret, long expirationTimeMs) {
        this.secret = secret;
        this.expirationTimeMs = expirationTimeMs;
    }

    @Override
    public String generateToken(User user) {
        return buildToken(user);
    }

    @Override
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username));
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String buildToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("id", user.getUuid().toString())
                .claim("role", user.getRole().name())
                .claim("enabled", user.isEnabled())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

}
