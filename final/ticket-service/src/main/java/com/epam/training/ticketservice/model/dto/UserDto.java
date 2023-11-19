package com.epam.training.ticketservice.model.dto;

import com.epam.training.ticketservice.model.entity.User;
import lombok.Getter;

@Getter
public class UserDto {

    private final String username;
    private final User.Role role;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}