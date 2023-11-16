package com.epam.training.ticketservice.ui.configuration;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class InMemoryDatabaseInitializer {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    @PostConstruct
    public void init() {
        User admin = new User("admin", "admin", User.Role.ADMIN);
        userRepository.save(admin);
        //Movie movie = new Movie("Spirited Away","Animation",120);
        //movieRepository.save(movie);
        // Room room = new Room("Pedersoli",8,7);
        //roomRepository.save(room);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //Screening screening = new Screening("Spirited Away","Pedersoli" , LocalDateTime.parse("2021-03-15 11:00", formatter));
        //screeningRepository.save(screening);
    }
}