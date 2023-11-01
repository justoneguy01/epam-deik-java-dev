package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> login(String username, String password);

    Optional<UserDTO> logout();

    Optional<UserDTO> describe();

    void registerUser(String username, String password);
}