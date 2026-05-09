package com.example.spotter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    private UUID uuid;
    private String token;
    private TokenType tokenType;
    private LocalDateTime expiresAt;
    private UUID userId;
}
