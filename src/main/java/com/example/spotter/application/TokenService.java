package com.example.spotter.application;

import com.example.spotter.application.exception.InvalidTokenException;
import com.example.spotter.domain.Token;
import com.example.spotter.domain.TokenType;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.EmailPort;
import com.example.spotter.port.out.TokenPort;
import com.example.spotter.port.out.TokenRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService implements TokenPort {

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    private final EmailPort emailPort;
    private final TokenRepositoryPort tokenRepositoryPort;
    private final TemplateEngine templateEngine;

    public TokenService(EmailPort emailPort, TokenRepositoryPort tokenRepositoryPort, TemplateEngine templateEngine) {
        this.emailPort = emailPort;
        this.tokenRepositoryPort = tokenRepositoryPort;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendVerificationToken(User user) {
        var token = Token.builder()
                .tokenType(TokenType.ACTIVATION)
                .userId(user.getUuid())
                .token(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();
        tokenRepositoryPort.save(token);

        var activationLink = baseUrl + "/api/auth/activate?token=" + token.getToken();
        var context = new Context();
        context.setVariable("firstName", user.getFirstName());
        context.setVariable("activationLink", activationLink);
        context.setVariable("expiresInHours", 24);
        var html = templateEngine.process("email/activate-account", context);
        emailPort.sendHtml(user.getEmail(), "Aktywuj swoje konto", html);
    }

    @Override
    public Token validateToken(String token) {
        return tokenRepositoryPort.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Token not found"));
    }

}
