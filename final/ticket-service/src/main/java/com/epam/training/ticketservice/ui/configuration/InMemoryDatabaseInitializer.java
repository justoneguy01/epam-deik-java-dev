package com.epam.training.ticketservice.ui.configuration;

import com.epam.training.ticketservice.model.entity.User;
import com.epam.training.ticketservice.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

    }
}