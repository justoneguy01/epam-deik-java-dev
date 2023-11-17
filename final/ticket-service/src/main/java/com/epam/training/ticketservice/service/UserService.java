package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;

public interface UserService {

    String signInPrivileged(String username, String password);

    String signOut();

    String describe();

    User getLoggedInUser();
}