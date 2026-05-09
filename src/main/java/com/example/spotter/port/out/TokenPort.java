package com.example.spotter.port.out;

import com.example.spotter.domain.Token;
import com.example.spotter.domain.User;

public interface TokenPort {
    void sendVerificationToken(User user);
    Token validateToken(String token);
}
