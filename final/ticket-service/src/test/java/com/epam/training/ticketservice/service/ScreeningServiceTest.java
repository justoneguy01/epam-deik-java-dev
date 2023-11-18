package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.entity.Movie;
import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.model.entity.Screening;
import com.epam.training.ticketservice.model.repository.MovieRepository;
import com.epam.training.ticketservice.model.repository.RoomRepository;
import com.epam.training.ticketservice.model.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.implementation.ScreeningServiceImpl;
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
    private final Movie MOVIE = new Movie("Star Wars", "Sci-fi",60);
    private final Room ROOM = new Room("Leonardo DiCaprio", 10,10);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Screening SCREENING = new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 16:00", formatter));

    @Test
    void testCreateScreeningWhenMovieAndRoomArePresentAndScreeningIsEmpty() {
        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        // When
        when(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).thenReturn(Optional.of(MOVIE));
        when(roomRepository.findByRoomName(ROOM.getRoomName())).thenReturn(Optional.of(ROOM));
        when(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening())).thenReturn(Optional.empty());
        String actual = underTest.createScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening());
        // Then
        verify(screeningRepository).save(SCREENING);
        assertEquals("Screening successfully created", actual);
    }

    @Test
    void testCreateScreeningWhenMovieAndRoomEmpty() {
        // Given
        // When
        when(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).thenReturn(Optional.empty());
        when(roomRepository.findByRoomName(ROOM.getRoomName())).thenReturn(Optional.empty());
        String actual = underTest.createScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening());

        // Then
        assertEquals("The Movie or the Room does not exist", actual);
        verify(screeningRepository, never()).save(SCREENING);

    }


    void testCreateScreeningWhenMovieAndRoomArePresentAndHasNoOverlapAndHasNoBreakPeriod() {
        // Given
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        given(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening())).willReturn(Optional.of(SCREENING));

        // When
        when(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).thenReturn(Optional.of(MOVIE));
        when(roomRepository.findByRoomName(ROOM.getRoomName())).thenReturn(Optional.of(ROOM));
        when(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening())).thenReturn(Optional.of(SCREENING));
        String actual = underTest.createScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(),  LocalDateTime.parse("2023-11-15 17:11", formatter));
        // Then
        verify(screeningRepository).save(any(Screening.class));
        assertEquals("This would start in the break period after another screening in this room",actual);
    }


    @Test
    void testCheckHasOverlapShouldReturnTrue() {
        // Given
        Screening testScreening= new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 16:59", formatter));
        Movie movie = new Movie("Star Wars", "Sci-fi", 60);
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
        Screening testScreening= new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 19:00", formatter));
        Movie movie = new Movie("Star Wars", "Sci-fi", 60);
        List<Screening> screenings = List.of(SCREENING);
        // When
        when(screeningRepository.findAll()).thenReturn(screenings);
        boolean actual = underTest.checkHasOverlap(testScreening.getBeginScreening(), movie, testScreening.getRoomName());
        // Then
        assertFalse(actual, "Screening successfully created");
    }
    @Test
    void testCheckHasBreakPeriodShouldReturnTrue() {
        // Given
        Screening testScreening = new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 17:09", formatter));
        Movie movie = new Movie("Star Wars", "Sci-fi", 60);
        List<Screening> screenings = List.of(SCREENING);
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        given(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening())).willReturn(Optional.of(SCREENING));

        // When
        when(screeningRepository.findAll()).thenReturn(screenings);
        boolean actual = underTest.checkHasBreakPeriod(testScreening.getBeginScreening(), movie, testScreening.getRoomName());

        // Then
        assertTrue(actual, "This would start in the break period after another screening in this room");
    }

    @Test
    void testCheckHasBreakPeriodShouldReturnFalse() {
        // Given
        Screening testScreening = new Screening("Star Wars", "Leonardo DiCaprio", LocalDateTime.parse("2023-11-15 17:11", formatter));
        Movie movie = new Movie("Star Wars", "Sci-fi", 60);
        List<Screening> screenings = List.of(SCREENING);
        given(movieRepository.findByMovieTitle(MOVIE.getMovieTitle())).willReturn(Optional.of(MOVIE));
        given(roomRepository.findByRoomName(ROOM.getRoomName())).willReturn(Optional.of(ROOM));
        given(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(MOVIE.getMovieTitle(), ROOM.getRoomName(), SCREENING.getBeginScreening())).willReturn(Optional.of(SCREENING));

        // When
        when(screeningRepository.findAll()).thenReturn(screenings);
        boolean actual = underTest.checkHasBreakPeriod(testScreening.getBeginScreening(), movie, testScreening.getRoomName());

        // Then
        assertFalse(actual, "Screening successfully created");
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

    @Test
    void testListScreeningsWhenScreeningsAreAlreadyExist() {
        // Given
        Movie testMovie = new Movie("Inception", "Heist", 60);
        Room testRoom = new Room("Leonardo DiCaprio", 10, 10);
        Screening testScreening1 = new Screening(testMovie.getMovieTitle(), testRoom.getRoomName(), LocalDateTime.parse("2023-11-15 18:00", formatter));
        Screening testScreening2 = new Screening(testMovie.getMovieTitle(), testRoom.getRoomName(), LocalDateTime.parse("2023-11-15 20:00", formatter));
        List<Screening> screenings = List.of(testScreening1, testScreening2);

        given(movieRepository.findByMovieTitle(testMovie.getMovieTitle())).willReturn(Optional.of(testMovie));
        given(movieRepository.findByMovieTitle(testMovie.getMovieTitle())).willReturn(Optional.of(testMovie));
        given(roomRepository.findByRoomName(testRoom.getRoomName())).willReturn(Optional.of(testRoom));
        given(roomRepository.findByRoomName(testRoom.getRoomName())).willReturn(Optional.of(testRoom));
        given(screeningRepository.findAll()).willReturn(screenings);

        // When
        String actual = underTest.listScreenings();
        String expected =
                testMovie + ", screened in room "+ testRoom.getRoomName()
                + "," + " at " +  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(testScreening1.getBeginScreening()) + "\n"
                + testMovie + ", screened in room "+ testRoom.getRoomName()
                + "," + " at " +  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(testScreening2.getBeginScreening()) + "\n";
        // Then

        assertEquals(expected, actual);
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
