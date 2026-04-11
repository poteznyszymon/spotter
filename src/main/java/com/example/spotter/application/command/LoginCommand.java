package com.example.spotter.application.command;

import com.example.spotter.domain.User;

public record LoginCommand(String email, String password) {

    public User toUser() {
        return new User(email, null, null, password);
    }

}
