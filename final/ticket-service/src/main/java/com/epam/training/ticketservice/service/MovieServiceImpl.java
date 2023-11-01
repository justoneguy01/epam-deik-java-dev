package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    @Override
    public void createMovie(String movieTitle, String genre, int lengthInMinutes) {
        try {
            Movie movie = new Movie(movieTitle, genre, lengthInMinutes);
            movieRepository.save(movie);
        }catch (Exception e){
            throw new IllegalArgumentException("This movie already exist.");
        }
    }

    @Override
    public void updateMovie(String movieTitle, String genre, int lengthInMinutes) {
        if (movieRepository.findByMovieTitle(movieTitle).isPresent()) {
            Movie movie = movieRepository.findByMovieTitle(movieTitle).get();
            movie.setGenre(genre);
            movie.setLengthInMinutes(lengthInMinutes);
            movieRepository.save(movie);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }
    @Override
    public void deleteMovie(String movieTitle) {
        if (movieRepository.findByMovieTitle(movieTitle).isPresent()) {
            Movie movie = movieRepository.findByMovieTitle(movieTitle).get();
            movieRepository.delete(movie);
        } else {
            throw new IllegalArgumentException("Movie not found");
        }
    }

    @Override
    public List<Movie> listMovies() {
        return movieRepository.findAll();
    }
}
