package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "You can add movies with admin privilege.")
    public String createMovie(String movieTitle, String genre, int length) {
        return movieService.addMovie(movieTitle, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "You can update movies with admin privilege.")
    public String updateMovie(String movieTitle, String genre, int length) {
        return movieService.updateMovie(movieTitle, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "You can delete movies with admin privilege.")
    public String  deleteMovie(String movieTitle) {
        return movieService.deleteMovie(movieTitle);
    }

    // <Film címe> (<műfaj>, <vetítés hossza percben> minutes)
    @ShellMethod(key = "list movies", value = "You can list movies.")
    public String listMovies() {
        return movieService.listMovies();
    }

    // Ebben a hiearahiában hogyan tudom megoldani hogy ne legyen kód ismétlés
    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
