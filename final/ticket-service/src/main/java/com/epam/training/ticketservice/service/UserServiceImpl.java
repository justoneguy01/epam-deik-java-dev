package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private UserDTO loggedInUser = null;
    @Override
    public Optional<UserDTO> login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDTO(user.get().getUsername(), user.get().getRole());
        return describe();
    }
    @Override
    public Optional<UserDTO> logout() {
        Optional<UserDTO> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }
    @Override
    public Optional<UserDTO> describe() {
        return Optional.ofNullable(loggedInUser);
    }
    @Override
    public void registerUser(String username, String password) {
        User user = new User(username, password, User.Role.USER);
        userRepository.save(user);
    }
}