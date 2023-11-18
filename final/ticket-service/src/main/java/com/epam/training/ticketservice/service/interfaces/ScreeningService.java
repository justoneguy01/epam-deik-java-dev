package com.epam.training.ticketservice.service.interfaces;

import java.time.LocalDateTime;

public interface ScreeningService {
    String createScreening(String movieTitle, String roomName, LocalDateTime beginScreening);

    String deleteScreening(String movieTitle, String roomName, LocalDateTime beginScreening);

    String listScreenings();
}
