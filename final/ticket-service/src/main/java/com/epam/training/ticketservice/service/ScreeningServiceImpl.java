package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        if (movieOptional.isPresent() && roomOptional.isPresent() && screeningRepository.count() == 0) {
            Movie movie = movieOptional.get();
            Room room = roomOptional.get();
            Screening screeningToSave = new Screening(movieTitle, roomName, beginScreening);
            screeningRepository.save(screeningToSave);
            return "Screening successfully created";

        } else if (movieOptional.isPresent() && roomOptional.isPresent() && screeningRepository.count() > 0) {
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
        return "The Movie or the Room does not exist";
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
        boolean hasOverlap = false;
        boolean hasBreakPeriod = false;
        LocalDateTime endScreening = beginScreening.plusMinutes(movie.getLengthInMinutes());
        for (var screening : screeningList) {
            LocalDateTime iteratorBeginDate = screening.getBeginScreening();
            LocalDateTime iteratorEndDate = iteratorBeginDate.plusMinutes(movie.getLengthInMinutes());
            if (beginScreening.plusMinutes(10).isBefore(iteratorEndDate)
                    && endScreening.plusMinutes(10).isAfter(iteratorBeginDate)) {
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
    public List<Screening> listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        if (screenings.isEmpty()) {
            return screenings;
        } else {
            return screenings;
        }
    }
}
