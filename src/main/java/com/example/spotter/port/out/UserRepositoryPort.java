package com.example.spotter.port.out;

import com.example.spotter.domain.User;

import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
