package com.example.spotter.adapter.out.persistence.repository;

import com.example.spotter.adapter.out.persistence.models.TokenEntity;
import com.example.spotter.domain.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, UUID> {
    Optional<TokenEntity> findByToken(String token);
    Optional<TokenEntity> findByUser_UuidAndTokenType(UUID userId, TokenType tokenType);

    @Modifying
    @Query("DELETE FROM TokenEntity t WHERE t.user.uuid = :userId AND t.tokenType = :tokenType")
    void deleteByUser_UuidAndTokenType(UUID userId, TokenType tokenType);
}
