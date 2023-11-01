package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void createMovie(String movieTitle, String genre, int lengthInMinutes);

    void updateMovie(String movieTitle, String genre, int lengthInMinutes);

    void deleteMovie(String movieTitle);

    List<Movie> listMovies();
}
