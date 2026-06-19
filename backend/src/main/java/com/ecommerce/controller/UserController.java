package com.ecommerce.controller;

import com.ecommerce.dto.UserProfileDto;
import com.ecommerce.entity.User;
import com.ecommerce.service.UserService;
import java.security.Principal;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfileDto me(Principal principal) {
        User user = userService.getByEmail(principal.getName());
        return UserProfileDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(Set.copyOf(user.getRoles().stream().map(Enum::name).toList()))
                .build();
    }
}
