package com.epam.training.ticketservice.service.interfaces;

import com.epam.training.ticketservice.model.entity.User;

public interface UserService {

    String signInPrivileged(String username, String password);

    String signOut();

    String describe();

    User getLoggedInUser();
}