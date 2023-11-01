package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.MovieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieContainer movieContainer;

    @Autowired
    public MovieService(MovieContainer movieContainer) {
        this.movieContainer = movieContainer;
    }
    public String addMovie(String movieTitle, String genre, int length) {
        List<Movie> movies = movieContainer.getMovies();
        if (movies == null) {
            movies = new ArrayList<>();
        }
        movies.add(new Movie(movieTitle, genre, length));
        movieContainer.setMovies(movies);
        return movieTitle + " successfully created.";
    }
    public String updateMovie(String movieTitle, String newGenre, int newLengthInMinutes) {
        List<Movie> movies = movieContainer.getMovies();
        if (movies == null || movies.isEmpty()) {
            return "No movies available to update.";
        }
        for (Movie movie : movies) {
            if (movie.getMovieTitle().equals(movieTitle)) {
                movie.setGenre(newGenre);
                movie.setLengthInMinutes(newLengthInMinutes);
                break;
            }
        }
        movieContainer.setMovies(movies);
        return movieTitle + " successfully updated.";
    }
    public String deleteMovie(String movieTitle) {
        List<Movie> movies = movieContainer.getMovies();
        if (movies == null || movies.isEmpty()) {
            return "No movies available to delete.";
        }
        boolean movieFound = false;
        for (Movie movie : movies) {
            if (movie.getMovieTitle().equals(movieTitle)) {
                movies.remove(movie);
                movieFound = true;
                break;
            }
        }
        if (movieFound) {
            movieContainer.setMovies(movies);
            return movieTitle + " successfully deleted.";
        } else {
            return movieTitle + " not found. No movies were deleted.";
        }
    }

    public String listMovies() {
        List<Movie> movies = movieContainer.getMovies();
        if (movies == null || movies.isEmpty()){
            return "No movies available.";
        } else {
            return movies.toString();
        }
    }
}
