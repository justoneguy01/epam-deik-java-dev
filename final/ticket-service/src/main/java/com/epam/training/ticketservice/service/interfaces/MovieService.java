package com.epam.training.ticketservice.service.interfaces;

import com.epam.training.ticketservice.model.entity.Movie;

import java.util.List;

public interface MovieService {
    String createMovie(String movieTitle, String genre, int lengthInMinutes);

    String updateMovie(String movieTitle, String genre, int lengthInMinutes);

    String deleteMovie(String movieTitle);

    List<Movie> listMovies();
}
