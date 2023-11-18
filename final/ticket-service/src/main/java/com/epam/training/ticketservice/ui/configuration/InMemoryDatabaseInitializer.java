package com.epam.training.ticketservice.ui.configuration;

import com.epam.training.ticketservice.model.entity.User;
import com.epam.training.ticketservice.model.repository.MovieRepository;
import com.epam.training.ticketservice.model.repository.RoomRepository;
import com.epam.training.ticketservice.model.repository.ScreeningRepository;
import com.epam.training.ticketservice.model.repository.UserRepository;
import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final UserService userService;

    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);

    }
}