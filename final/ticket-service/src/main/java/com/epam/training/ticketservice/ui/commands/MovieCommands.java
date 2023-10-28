package com.epam.training.ticketservice.ui.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class MovieCommands {
    @ShellMethod(key = "create movie", value = "You can add movies with admin privilege.")
    public String createMovie(String movieTitle, String genre, int length) {
        return null;
    }
    @ShellMethod(key = "update movie", value = "You can update movies with admin privilege.")
    public String updateMovie(String movieTitle, String genre, int length) {
        return null;
    }
    @ShellMethod(key = "delete movie", value = "You can delete movies with admin privilege.")
    public String deleteMovie(String movieTitle) {
        return null;
    }

    // <Film címe> (<műfaj>, <vetítés hossza percben> minutes
    @ShellMethod(key = "list movies", value = "You can list movies.")
    public String listMovies(String movieTitle) {
        return null;
    }

}
