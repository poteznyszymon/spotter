package com.example.spotter.port.out;

import com.example.spotter.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
}
