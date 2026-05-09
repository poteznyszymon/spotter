package com.example.spotter.adapter.out.tokenRepository;

import com.example.spotter.adapter.out.persistence.models.TokenEntity;
import com.example.spotter.adapter.out.persistence.models.UserEntity;
import com.example.spotter.domain.Token;

public class TokenMapper {

    Token toDomain(TokenEntity entity) {
        var token = new Token();
        token.setUuid(entity.getUuid());
        token.setToken(entity.getToken());
        token.setTokenType(entity.getTokenType());
        token.setExpiresAt(entity.getExpiresAt());
        token.setUserId(entity.getUser().getUuid());
        return token;
    }

    TokenEntity toEntity(Token domain) {
        var entity = new TokenEntity();
        entity.setUuid(domain.getUuid());
        entity.setToken(domain.getToken());
        entity.setTokenType(domain.getTokenType());
        entity.setExpiresAt(domain.getExpiresAt());
        var userRef = new UserEntity();
        userRef.setUuid(domain.getUserId());
        entity.setUser(userRef);
        return entity;
    }

}
