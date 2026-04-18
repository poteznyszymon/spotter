package com.example.spotter.adapter.out.userRepository;

import com.example.spotter.adapter.out.persistence.repository.UserJpaRepository;
import com.example.spotter.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRepositoryConfiguration {

    @Bean
    public UserRepositoryPort userRepositoryPort(UserJpaRepository userJpaRepository) {
        return new UserRepositoryAdapter(userJpaRepository);
    }

}
