package com.example.spotter.configuration;

import com.example.spotter.domain.Role;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.JwtPort;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt-properties.accessTokenName:token}")
    private String accessTokenName;

    private final JwtPort jwtPort;

    public JwtAuthenticationFilter(JwtPort jwtPort) {
        this.jwtPort = jwtPort;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String token = extractTokenFromHeader(request);
        if (token == null) {
            token = extractTokenFromCookies(request);
        }

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final var username = jwtPort.extractClaim(token, Claims::getSubject);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtPort.isTokenValid(token, username)) {
                    var user = new User();
                    user.setUuid(UUID.fromString(jwtPort.extractClaim(token, claims -> claims.get("id", String.class))));
                    user.setUsername(username);
                    user.setRole(jwtPort.extractClaim(token, claims -> Role.valueOf(claims.get("role", String.class))));
                    user.setEnabled(jwtPort.extractClaim(token, claims -> claims.get("enabled", Boolean.class)));
                    user.setEmail(jwtPort.extractClaim(token, claims -> claims.get("email", String.class)));
                    user.setFirstName(jwtPort.extractClaim(token, claims -> claims.get("firstName", String.class)));
                    user.setLastName(jwtPort.extractClaim(token, claims -> claims.get("lastName", String.class)));

                    var userDetails = new DomainUserDetails(user);
                    var authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            /// TODO check jwt exceptions and return to user info what's wrong
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if (accessTokenName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
