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
public class ScreeningServiceImpl implements ScreeningService{
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    @Override
    public String createScreening(String movieTitle, String roomName, LocalDateTime beginScreening) {
        Optional<Movie> movieOptional = movieRepository.findByMovieTitle(movieTitle);
        Optional<Room> roomOptional = roomRepository.findByRoomName(roomName);

        if (movieOptional.isPresent() && roomOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Room room = roomOptional.get();
            LocalDateTime endScreening = beginScreening.plusMinutes(movie.getLengthInMinutes());

            if (screeningRepository != null) {
                List<Screening> screeningList = screeningRepository.findAll();
                boolean hasOverlap = false;
                boolean hasBreakPeriod = false;

                for (var screening : screeningList) {
                    LocalDateTime iteratorBeginDate = screening.getBeginScreening();
                    LocalDateTime iteratorEndDate = iteratorBeginDate.plusMinutes(movie.getLengthInMinutes());
                    // debug, must delete later
                    //System.out.println(iteratorBeginDate + " " +iteratorEndDate);
                    //System.out.println(beginScreening + " " +endScreening.plusMinutes(10));
                    //System.out.println(beginScreening.plusMinutes(10).isAfter(iteratorEndDate));
                    if (beginScreening.isBefore(iteratorEndDate) && endScreening.isAfter(iteratorBeginDate) && screening.getRoomName().equals(roomName)) {
                        hasOverlap = true;
                        break;
                    } else if (beginScreening.plusMinutes(10).isBefore(iteratorEndDate) && endScreening.plusMinutes(10).isAfter(iteratorBeginDate)) {
                        hasBreakPeriod = true;
                        break;
                    }
                }

                if (hasOverlap) {
                    return "There is an overlapping screening";
                } else if (hasBreakPeriod) {
                    return "This would start in the break period after another screening in this room";
                } else {
                    Screening screeningToSave = new Screening(movieTitle, roomName, beginScreening);
                    screeningRepository.save(screeningToSave);
                    return "Screening successfully created";
                }
            } else {
                Screening screeningToSave = new Screening(movieTitle, roomName, beginScreening);
                screeningRepository.save(screeningToSave);
                return "Screening successfully created";
            }
        }
        return "The Movie or the Room does not exist";
    }

    @Override
    public String deleteScreening(String movieTitle, String roomName, LocalDateTime beginScreening) {
        if (screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(movieTitle, roomName, beginScreening).isPresent()){
            screeningRepository.delete(screeningRepository.findByMovieTitleAndRoomNameAndBeginScreening(movieTitle, roomName, beginScreening).get());
            return "Screening successfully deleted";
        }else {
            return "Screening not found";
        }
    }

    @Override
    public List<Screening> listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();
        if (screenings==null) {
            return null;
        } else {
            return screenings;
        }
    }
}
