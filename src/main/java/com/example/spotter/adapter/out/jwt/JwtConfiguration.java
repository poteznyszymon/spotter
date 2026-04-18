package com.example.spotter.adapter.out.jwt;

import com.example.spotter.port.out.TokenPort;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt-properties")
public class JwtConfiguration {

    @Setter
    private String secret;
    @Setter
    private long expirationTimeMs;

    @Bean
    TokenPort tokenPort() {
        return new JwtAdapter(secret, expirationTimeMs);
    }

}
