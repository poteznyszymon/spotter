package com.example.spotter.adapter.in.web.dto;

import com.example.spotter.domain.User;

import java.util.UUID;

public record UserDTO(UUID uuid, String username) {

    public static UserDTO fromDomain(User user) {
        return new UserDTO(user.getUuid(), user.getUsername());
    }

}
