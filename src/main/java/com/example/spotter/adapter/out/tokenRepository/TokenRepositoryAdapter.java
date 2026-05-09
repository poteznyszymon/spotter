package com.example.spotter.adapter.out.tokenRepository;

import com.example.spotter.adapter.out.persistence.repository.TokenJpaRepository;
import com.example.spotter.domain.Token;
import com.example.spotter.domain.TokenType;
import com.example.spotter.port.out.TokenRepositoryPort;

import java.util.Optional;
import java.util.UUID;

public class TokenRepositoryAdapter implements TokenRepositoryPort {

    private final TokenJpaRepository tokenJpaRepository;
    private final TokenMapper tokenMapper;

    public TokenRepositoryAdapter(TokenJpaRepository tokenJpaRepository, TokenMapper tokenMapper) {
        this.tokenJpaRepository = tokenJpaRepository;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public Token save(Token token) {
        var entity = tokenMapper.toEntity(token);
        var savedEntity = tokenJpaRepository.save(entity);
        return tokenMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Token> findByUuid(UUID uuid) {
        return tokenJpaRepository.findById(uuid).map(tokenMapper::toDomain);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenJpaRepository.findByToken(token).map(tokenMapper::toDomain);
    }

    @Override
    public Optional<Token> findByUserAndTokenType(UUID userId, TokenType tokenType) {
        return tokenJpaRepository.findByUser_UuidAndTokenType(userId, tokenType).map(tokenMapper::toDomain);
    }

    @Override
    public void delete(Token token) {
        var entity = tokenMapper.toEntity(token);
        tokenJpaRepository.delete(entity);
    }

    @Override
    public void deleteByUserIdAndTokenType(UUID userId, TokenType tokenType) {
        tokenJpaRepository.deleteByUser_UuidAndTokenType(userId, tokenType);
    }

}
