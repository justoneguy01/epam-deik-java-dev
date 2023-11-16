package com.epam.training.ticketservice.service;

public interface UserService {
    String signInPrivileged(String username, String password);
    String signOut();
    String describe();
}