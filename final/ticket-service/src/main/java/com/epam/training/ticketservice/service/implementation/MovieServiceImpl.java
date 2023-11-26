package com.epam.training.ticketservice.service.implementation;

import com.epam.training.ticketservice.model.entity.Movie;
import com.epam.training.ticketservice.model.repository.MovieRepository;
import com.epam.training.ticketservice.service.interfaces.MovieService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public String createMovie(String movieTitle, String genre, int lengthInMinutes) {

        if (movieRepository.findByMovieTitle(movieTitle).isPresent()) {
            throw new IllegalArgumentException("This movie is already exist!");
        } else {
            Movie movie = new Movie(movieTitle, genre, lengthInMinutes);
            movieRepository.save(movie);
            return movieTitle + " successfully added";
        }

    }

    @Override
    public String updateMovie(String movieTitle, String genre, int lengthInMinutes) {

        if (movieRepository.findByMovieTitle(movieTitle).isPresent()) {
            Movie movie = movieRepository.findByMovieTitle(movieTitle).get();
            movie.setGenre(genre);
            movie.setLengthInMinutes(lengthInMinutes);
            movieRepository.save(movie);
            return movieTitle + " successfully updated";
        } else {
            throw new IllegalArgumentException("The " + movieTitle + " is not found!");
        }
    }

    @Override
    public String deleteMovie(String movieTitle) {

        if (movieRepository.findByMovieTitle(movieTitle).isPresent()) {
            Movie movie = movieRepository.findByMovieTitle(movieTitle).get();
            movieRepository.delete(movie);
            return movieTitle + " successfully deleted";

        } else {

            throw new IllegalArgumentException("The " + movieTitle + " is not found!");

        }
    }

    @Override
    public String listMovies() {

        List<Movie> movies = movieRepository.findAll();

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
}
