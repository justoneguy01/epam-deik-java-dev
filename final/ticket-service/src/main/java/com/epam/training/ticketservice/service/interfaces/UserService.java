package com.epam.training.ticketservice.service.interfaces;


import com.epam.training.ticketservice.model.dto.UserDto;

import java.util.Optional;

public interface UserService {

    String signInPrivileged(String username, String password);

    String signOut();

    String describe();

    UserDto getLoggedUser();

}