package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.service.interfaces.MovieService;
import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    private final MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "You can add movies with admin privilege.")
    public String createMovie(String movieTitle, String genre, int length) {
        return movieService.createMovie(movieTitle, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "You can update movies with admin privilege.")
    public String updateMovie(String movieTitle, String genre, int length) {
        return movieService.updateMovie(movieTitle, genre, length);
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "You can delete movies with admin privilege.")
    public String deleteMovie(String movieTitle) {
        return movieService.deleteMovie(movieTitle);
    }

    @ShellMethod(key = "list movies", value = "You can list movies.")
    public String listMovies() {
        return movieService.listMovies();
    }

    private Availability isAvailable() {
        return userService.getLoggedUser().getUsername().equals("admin")
                ? Availability.available()
                : Availability.unavailable("You are not authorized");
    }
}
