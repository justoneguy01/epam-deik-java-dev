package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.service.MovieServiceImpl;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class MovieCommands {

    MovieServiceImpl movieServiceImpl;
    private final UserService userService;

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "You can add movies with admin privilege.")
    public String createMovie(String movieTitle, String genre, int length) {
        return movieServiceImpl.createMovie(movieTitle, genre, length);
    }

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "You can update movies with admin privilege.")
    public String updateMovie(String movieTitle, String genre, int length) {
        return movieServiceImpl.updateMovie(movieTitle, genre, length);
    }

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "You can delete movies with admin privilege.")
    public String deleteMovie(String movieTitle) {
        return movieServiceImpl.deleteMovie(movieTitle);
    }

    @ShellMethod(key = "list movies", value = "You can list movies.")
    public String listMovies() {
        List<Movie> movies = movieServiceImpl.listMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            String movieString = "";
            for (Movie movie : movies) {
                movieString += movie.toString() + "\n";
            }
            return movieString;
        }
    }

    private Availability isAvailable() {
        if (userService.describe() != null) {
            return Availability.available();
        } else {
            return  Availability.unavailable("You need to sign in as admin");
        }
    }
}
