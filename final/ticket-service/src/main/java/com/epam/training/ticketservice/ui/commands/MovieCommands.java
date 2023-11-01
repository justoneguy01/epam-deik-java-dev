package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.MovieServiceImpl;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    MovieServiceImpl movieServiceImpl;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "You can add movies with admin privilege.")
    public String createMovie(String movieTitle, String genre, int length) {
        movieServiceImpl.createMovie(movieTitle, genre, length);
        return "Movie added successfully!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "You can update movies with admin privilege.")
    public String updateMovie(String movieTitle, String genre, int length) {
        movieServiceImpl.updateMovie(movieTitle, genre, length);
        return "Movie updated successfully";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "You can delete movies with admin privilege.")
    public String  deleteMovie(String movieTitle) {
        movieServiceImpl.deleteMovie(movieTitle);
        return movieTitle + " deleted successfully";
    }

    // <Film címe> (<műfaj>, <vetítés hossza percben> minutes)
    @ShellMethod(key = "list movies", value = "You can list movies.")
    public List<Movie> listMovies() {
        return movieServiceImpl.listMovies();
    }

    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
