package com.example.spotter.adapter.in.web.controller;

import com.example.spotter.application.UserService;
import com.example.spotter.configuration.DomainUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User related operations")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/avatar")
    public void uploadAvatar(@RequestBody MultipartFile file, @AuthenticationPrincipal DomainUserDetails userDetails) throws IOException {
        userService.uploadAvatar(userDetails.user().getUuid(), file);
    }

}
