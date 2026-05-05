package com.example.spotter.port.out;

import com.example.spotter.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {
    void save(User user);
    Optional<User> findByUsername(String username);
    Optional<User> findById(UUID id);
    boolean existsByUsername(String username);
}
