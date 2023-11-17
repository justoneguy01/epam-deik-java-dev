package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Screening;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    String createScreening(String movieTitle, String roomName, LocalDateTime beginScreening);

    String deleteScreening(String movieTitle, String roomName, LocalDateTime beginScreening);

    String listScreenings();
}
