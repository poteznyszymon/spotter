package com.example.spotter.application.command;

import com.example.spotter.domain.User;

public record LoginCommand(String username, String password) {

    public User toUser() {
        return new User(username, password);
    }

}
