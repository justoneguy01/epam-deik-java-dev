package service_test;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class MovieServiceTest {
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);
    private Movie MOVIE = new Movie("Star Wars", "Sci-fi",180);

    @Test // CreateMovie +
    void testCreateMovieWhenMovieIsNotExist() {
        //Given
        //When
        underTest.createMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes());
        //Then
        verify(movieRepository).save(MOVIE);
    }

    @Test // CreateMovie -
    void testCreateMovieWhenMovieIsAlreadyExist() {
        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.createMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes()));
        // Then
        verify(movieRepository, never()).save(MOVIE);
    }

    @Test // UpdateMovie +
    void testUpdateMovieWhenMovieIsAlreadyExist() {
        //Given
        Movie expected = new Movie("Star Wars", "Action", 120);
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        //When
        underTest.updateMovie("Star Wars", "Action", 120);
        //Then
        verify(movieRepository).save(expected);
    }

    @Test // UpdateMovie -
    void testUpdateMovieWhenMovieIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () -> underTest.updateMovie(MOVIE.getMovieTitle(), MOVIE.getGenre(), MOVIE.getLengthInMinutes()));
        // Then
        verify(movieRepository, never()).save(MOVIE);
    }

    @Test // DeleteMovie +
    void testDeleteMovieWhenMovieIsAlreadyExist() {
        //Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        //When
        underTest.deleteMovie(MOVIE.getMovieTitle());
        //Then
        verify(movieRepository).delete(MOVIE);
    }

    @Test // DeleteMovie -
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
        given(movieRepository.findAll()).willReturn(List.of(MOVIE));
        // When
        List<Movie> actual = underTest.listMovies();
        // Then
        assertEquals(List.of(MOVIE), actual);
        verify(movieRepository).findAll();

    }

    @Test
    void testListMoviesWhenMoviesAreNotAlreadyExist() {
        // Given
        // When
        List<Movie> result = underTest.listMovies();
        // Then
        assertTrue(result.isEmpty());
        verify(movieRepository).findAll();
    }
}
