package com.example.spotter.port.out;


import com.example.spotter.domain.AuthResult;
import com.example.spotter.domain.User;

public interface FusionAuthPort {
    AuthResult register(User user);
    AuthResult login(User user);
}
