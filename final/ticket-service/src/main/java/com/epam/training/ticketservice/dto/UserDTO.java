package com.epam.training.ticketservice.dto;

import com.epam.training.ticketservice.model.User;

public record UserDTO(String username, User.Role role) {
}
