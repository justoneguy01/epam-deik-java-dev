package com.epam.training.ticketservice.model;

import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
        User user = new User("user","user",User.Role.USER);
        userRepository.save(user);
        Movie movie = new Movie("Interstellar","Sci-fi",169);
        movieRepository.save(movie);
        Room room = new Room("Pedersoli",8,7);
        roomRepository.save(room);
    }
}