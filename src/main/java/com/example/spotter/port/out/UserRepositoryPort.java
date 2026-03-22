package com.example.spotter.port.out;

import com.example.spotter.domain.User;

public interface UserRepositoryPort {
    void save(User user);
}
