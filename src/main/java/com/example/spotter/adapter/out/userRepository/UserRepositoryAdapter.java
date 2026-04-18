package com.example.spotter.adapter.out.userRepository;

import com.example.spotter.adapter.out.persistence.models.UserEntity;
import com.example.spotter.adapter.out.persistence.repository.UserJpaRepository;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.UserRepositoryPort;

import java.util.Optional;

public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void save(User user) {
        var userEntity = toEntity(user);
        userJpaRepository.save(userEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userJpaRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    UserEntity toEntity(User user) {
        var entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setRole(user.getRole());
        return entity;
    }

    User toDomain(UserEntity userEntity) {
        var user = new User();
        user.setUuid(userEntity.getUuid());
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setRole(userEntity.getRole());
        return user;
    }

}
