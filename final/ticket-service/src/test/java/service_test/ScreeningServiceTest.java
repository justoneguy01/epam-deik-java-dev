package service_test;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningServiceImpl;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class ScreeningServiceTest {
    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);
    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(movieRepository,roomRepository,screeningRepository);
    private Movie MOVIE = new Movie("Star Wars", "Sci-fi",180);
    private Room ROOM = new Room("Leonardo DiCaprio", 10,10);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Screening SCREENING = new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 16:00", formatter));
    @Test
    void testCreateScreeningWhenMovieAndRoomArePresentAndTheRepositoryIsEmpty() {
        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        // When
        when(movieRepository.findByMovieTitle(anyString())).thenReturn(Optional.of(MOVIE));
        when(roomRepository.findByRoomName(anyString())).thenReturn(Optional.of(ROOM));
        when(screeningRepository.count()).thenReturn(0L);
        String actual = underTest.createScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening());
        // Then
        verify(screeningRepository).save(SCREENING);
        assertEquals("Screening successfully created", actual);
    }


    void testCreateScreeningWhenMovieAndRoomArePresentAndTheRepositoryIsNotEmpty() {

    }

    //@Test
    void testCreateScreeningWhenMovieAndRoomArePresentAndTheRepositoryIsNotEmptyButOverlapping() {
    }

    @Test
    void testCheckHasOverlapShouldReturnTrue() {
        // Given
        Screening testScreening= new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 17:00", formatter));
        Movie movie = new Movie("Star Wars", "Action", 120);
        List<Screening> screenings = List.of(SCREENING);
        // When
        when(screeningRepository.findAll()).thenReturn(screenings);
        boolean actual = underTest.checkHasOverlap(testScreening.getBeginScreening(), movie, testScreening.getRoomName());
        // Then
        assertTrue(actual, "There should be an overlap with one of the screenings");
    }

    @Test
    void testCheckHasOverlapShouldReturnFalse() {
        // Given
        Screening testScreening= new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 18:00", formatter));
        Movie movie = new Movie("Star Wars", "Action", 120);
        List<Screening> screenings = List.of(SCREENING);
        // When
        when(screeningRepository.findAll()).thenReturn(screenings);
        boolean actual = underTest.checkHasOverlap(testScreening.getBeginScreening(), movie, testScreening.getRoomName());
        // Then
        assertFalse(actual, "There should be an overlap with one of the screenings");
    }

    @Test
    void testDeleteScreeningsWhenScreeningsIsAlreadyExist() {
        //Given
        given(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(SCREENING.getMovieTitle(),SCREENING.getRoomName(),SCREENING.getBeginScreening())).willReturn(Optional.of(SCREENING));
        //When
        underTest.deleteScreening(SCREENING.getMovieTitle(),SCREENING.getRoomName(),SCREENING.getBeginScreening());
        //Then
        verify(screeningRepository).delete(SCREENING);
    }

    @Test
    void testDeleteScreeningsWhenScreeningsIsNotExist() {
        // Given
        // When
        assertThrows(IllegalArgumentException.class, () ->  underTest.deleteScreening(SCREENING.getMovieTitle(),SCREENING.getRoomName(),SCREENING.getBeginScreening()));
        // Then
        verify(screeningRepository, never()).save(SCREENING);
    }

    void testListScreeningsWhenScreeningsAreAlreadyExist() {
    }

    @Test
    void testListMoviesWhenMoviesAreNotAlreadyExist() {
        // Given
        // When
        String result = underTest.listScreenings();
        // Then
        assertEquals("There are no screenings", result);
        verify(screeningRepository).findAll();
    }
}
