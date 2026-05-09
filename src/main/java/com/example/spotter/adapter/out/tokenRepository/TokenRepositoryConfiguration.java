package com.example.spotter.adapter.out.tokenRepository;

import com.example.spotter.adapter.out.persistence.repository.TokenJpaRepository;
import com.example.spotter.port.out.TokenRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenRepositoryConfiguration {

    @Bean
    public TokenRepositoryPort tokenRepositoryPort(TokenJpaRepository tokenJpaRepository) {
        return new TokenRepositoryAdapter(tokenJpaRepository, new TokenMapper());
    }

}
