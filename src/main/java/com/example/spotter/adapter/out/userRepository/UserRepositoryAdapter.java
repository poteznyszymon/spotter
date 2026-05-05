package com.example.spotter.adapter.out.userRepository;

import com.example.spotter.adapter.out.persistence.repository.UserJpaRepository;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.UserRepositoryPort;

import java.util.Optional;
import java.util.UUID;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(userMapper.toEntity(user));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findById(id).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

}
