package com.example.spotter.adapter.out.userRepository;

import com.example.spotter.adapter.out.persistence.models.UserEntity;
import com.example.spotter.adapter.out.persistence.repository.UserJpaRepository;
import com.example.spotter.domain.User;
import com.example.spotter.port.out.UserRepositoryPort;

public class StandardUserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public StandardUserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = toEntity(user);
        userJpaRepository.save(userEntity);
    }

    UserEntity toEntity(User user) {
        return null;
    }

}
