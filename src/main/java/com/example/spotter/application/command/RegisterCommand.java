package com.example.spotter.application.command;

import com.example.spotter.domain.User;

public record RegisterCommand(String email,
                              String username,
                              String firstName,
                              String lastName,
                              String password) {

    public User toUser() {
        return new User(email, username, firstName, lastName, password);
    }

}
