package com.epam.training.ticketservice.service.interfaces;


public interface MovieService {
    String createMovie(String movieTitle, String genre, int lengthInMinutes);

    String updateMovie(String movieTitle, String genre, int lengthInMinutes);

    String deleteMovie(String movieTitle);

    String listMovies();
}
