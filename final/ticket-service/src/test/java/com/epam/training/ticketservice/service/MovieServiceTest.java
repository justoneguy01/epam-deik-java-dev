package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.entity.Movie;
import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.model.entity.Screening;
import com.epam.training.ticketservice.model.repository.MovieRepository;
import com.epam.training.ticketservice.service.implementation.MovieServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);
    private Movie MOVIE = new Movie("Star Wars", "Sci-fi",180);

    private Movie MOVIE2 = new Movie("Inception", "Heist", 60);

    @Test
    void testCreateMovieWhenMovieIsNotExist() {
        //Given
        //When
        underTest.createMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes());
        //Then
        verify(movieRepository).save(MOVIE);
    }

    @Test
    void testCreateMovieWhenMovieIsAlreadyExist() {
        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.createMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes()));
        // Then
        verify(movieRepository, never()).save(MOVIE);
    }

    @Test
    void testUpdateMovieWhenMovieIsAlreadyExist() {
        //Given
        Movie expected = new Movie("Star Wars", "Action", 120);
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        //When
        underTest.updateMovie("Star Wars", "Action", 120);
        //Then
        verify(movieRepository).save(expected);
    }

    @Test
    void testUpdateMovieWhenMovieIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.updateMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes()));
        // Then
        verify(movieRepository, never()).save(MOVIE);
    }

    @Test
    void testDeleteMovieWhenMovieIsAlreadyExist() {
        //Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        //When
        underTest.deleteMovie(MOVIE.getMovieTitle());
        //Then
        verify(movieRepository).delete(MOVIE);
    }

    @Test
    void testDeleteMovieWhenMovieIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteMovie(MOVIE.getMovieTitle()));
        // Then
        verify(movieRepository, never()).save(MOVIE);
    }

    @Test
    void testListMovieWhenMoviesAreAlreadyExist() {

        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(movieRepository.findByMovieTitle(MOVIE2.getMovieTitle())).willReturn(Optional.of(MOVIE2));
        List<Movie> movies = List.of(MOVIE, MOVIE2);
        given(movieRepository.findAll()).willReturn(movies);

        // When
        String actual = underTest.listMovies();
        String expected = MOVIE.toString() + "\n" + MOVIE2.toString() + "\n";

        // Then
        assertEquals(expected, actual);

    }

    @Test
    void testListMoviesWhenMoviesAreNotAlreadyExist() {
        // Given
        // When
        String actual = underTest.listMovies();
        String expected = "There are no movies at the moment";
        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findAll();
    }
}
