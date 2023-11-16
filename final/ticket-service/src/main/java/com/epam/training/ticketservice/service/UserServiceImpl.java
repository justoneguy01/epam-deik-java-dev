package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private User loggedInUser = null;
    @Override
    public String signInPrivileged(String username, String password) {
        Optional<User> adminUser = userRepository.findByUsernameAndPassword(username, password);
        if (adminUser.isEmpty()) {
            return "Login failed due to incorrect credentials";
        }
        else{
            loggedInUser = adminUser.get();
            return "Login success";
        }
    }

    @Override
    public String signOut() {
        if (loggedInUser == null) {
            return "You are not signed in";
        }
        else{
            loggedInUser = null;
            return "Signed out";
        }
    }
    @Override
    public String describe() {
        if (loggedInUser != null)
        {
            return "Signed in with privileged account '"+loggedInUser.getUsername()+"'";
        }
        else{
            return "You are not signed in";
        }
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }
}