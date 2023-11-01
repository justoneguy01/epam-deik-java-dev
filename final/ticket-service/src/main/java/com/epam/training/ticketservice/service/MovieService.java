package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    String createMovie(String movieTitle, String genre, int lengthInMinutes);

    String updateMovie(String movieTitle, String genre, int lengthInMinutes);

    String deleteMovie(String movieTitle);

    List<Movie> listMovies();
}
