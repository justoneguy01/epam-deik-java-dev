package com.epam.training.ticketservice.service.implementation;

import com.epam.training.ticketservice.model.entity.Movie;
import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.model.entity.Screening;
import com.epam.training.ticketservice.model.repository.MovieRepository;
import com.epam.training.ticketservice.model.repository.RoomRepository;
import com.epam.training.ticketservice.model.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.interfaces.ScreeningService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;

    @Override
    public String createScreening(String movieTitle, String roomName, LocalDateTime beginScreening) {
        Optional<Movie> movieOptional = movieRepository.findByMovieTitle(movieTitle);
        Optional<Room> roomOptional = roomRepository.findByRoomName(roomName);
        Optional<Screening> screeningOptional = screeningRepository.findByRoomName(roomName);
        if (movieOptional.isPresent() && roomOptional.isPresent() && screeningOptional.isEmpty()) {
            Movie movie = movieOptional.get();
            Room room = roomOptional.get();
            Screening screeningToSave = new Screening(movieTitle, roomName, beginScreening);
            screeningRepository.save(screeningToSave);
            return "Screening successfully created";

        } else if (movieOptional.isEmpty() && roomOptional.isEmpty()) {

            return "The Movie or the Room does not exist";

        } else {
            Movie movie = movieOptional.get();
            Room room = roomOptional.get();
            if (checkHasOverlap(beginScreening, movie, roomName)) {
                return "There is an overlapping screening";
            } else if (checkHasBreakPeriod(beginScreening, movie, roomName)) {
                return "This would start in the break period after another screening in this room";
            }
            Screening screeningToSave = new Screening(movieTitle, roomName, beginScreening);
            screeningRepository.save(screeningToSave);
            return "Screening successfully created";
        }
    }

    public boolean checkHasOverlap(LocalDateTime beginScreening, Movie movie, String roomName) {
        List<Screening> screeningList = screeningRepository.findAll();
        LocalDateTime endScreening = beginScreening.plusMinutes(movie.getLengthInMinutes());
        for (var screening : screeningList) {
            LocalDateTime iteratorBeginDate =
                    screening.getBeginScreening();
            LocalDateTime iteratorEndDate =
                    iteratorBeginDate.plusMinutes(movie.getLengthInMinutes());
            if (beginScreening.isBefore(iteratorEndDate)
                    && endScreening.isAfter(iteratorBeginDate)
                    && screening.getRoomName().equals(roomName)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkHasBreakPeriod(LocalDateTime beginScreening, Movie movie, String roomName) {
        List<Screening> screeningList = screeningRepository.findAll();
        LocalDateTime endScreening = beginScreening.plusMinutes(movie.getLengthInMinutes() + 10);
        for (var screening : screeningList) {
            int currentLengthInMinutes = movieRepository.findByMovieTitle(
                    screening.getMovieTitle()).get().getLengthInMinutes();
            LocalDateTime iteratorBeginDate = screening.getBeginScreening();
            LocalDateTime iteratorEndDate = iteratorBeginDate.plusMinutes(currentLengthInMinutes);
            if (beginScreening.isAfter(iteratorEndDate)
                    && beginScreening.minusMinutes(10).isBefore(iteratorEndDate)
                    && screening.getRoomName().equals(roomName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String deleteScreening(String movieTitle, String roomName, LocalDateTime beginScreening) {

        if (screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(
                movieTitle, roomName, beginScreening).isPresent()) {
            screeningRepository.delete(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(
                    movieTitle, roomName, beginScreening).get());
            return "Screening successfully deleted";
        } else {
            throw new IllegalArgumentException("Screening not found");
        }
    }

    @Override
    public String listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        } else {
            String screeningString = "";
            for (Screening screening : screenings) {
                Movie currentMovie = movieRepository.findByMovieTitle(screening.getMovieTitle()).get();
                Room currentRoom = roomRepository.findByRoomName(screening.getRoomName()).get();
                screeningString += currentMovie.getMovieTitle() + " (" + currentMovie.getGenre() + ", "
                        + currentMovie.getLengthInMinutes() + " minutes), screened in room "
                        + currentRoom.getRoomName() + ", at " + DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm").format(screening.getBeginScreening()) + "\n";
            }
            return screeningString;
        }
    }
}
