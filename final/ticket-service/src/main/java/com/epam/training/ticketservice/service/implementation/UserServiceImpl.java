package com.epam.training.ticketservice.service.implementation;

import com.epam.training.ticketservice.model.dto.UserDto;
import com.epam.training.ticketservice.model.entity.User;
import com.epam.training.ticketservice.model.repository.UserRepository;
import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private UserDto loggedInUser = null;

    @Override
    public String signInPrivileged(String username, String password) {

        Optional<User> adminUser = userRepository.findByUsername(username);
        if (adminUser.isPresent() && adminUser.get().getUsername().equals(password)) {
            loggedInUser = new UserDto(adminUser.get());
            return "Signed in";
        } else {
            return "Login failed due to incorrect credentials";
        }
    }

    @Override
    public String signOut() {
        if (loggedInUser != null) {
            String user = loggedInUser.getUsername();
            loggedInUser = null;
            return "Signed out";
        } else {
            return "You are not signed in";
        }
    }

    @Override
    public String describe() {
        if (loggedInUser != null) {
            return "Signed in with privileged account '" + loggedInUser.getUsername() + "'";
        } else {
            return "You are not signed in";
        }
    }

    public UserDto getLoggedUser() {
        return loggedInUser;
    }
}