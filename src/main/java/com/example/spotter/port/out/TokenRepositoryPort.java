package com.example.spotter.port.out;

import com.example.spotter.domain.Token;
import com.example.spotter.domain.TokenType;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepositoryPort {
    Token save(Token token);
    Optional<Token> findByUuid(UUID uuid);
    Optional<Token> findByToken(String token);
    Optional<Token> findByUserAndTokenType(UUID userId, TokenType tokenType);
    void delete(Token token);
    void deleteByUserIdAndTokenType(UUID userId, TokenType tokenType);
}
