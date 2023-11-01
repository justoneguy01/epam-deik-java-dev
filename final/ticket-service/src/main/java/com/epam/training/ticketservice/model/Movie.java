package com.epam.training.ticketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Movie {
    private String movieTitle;
    private String genre;
    private int lengthInMinutes;

    @Override
    public String toString() {
        return  movieTitle + " ("+genre+", "+lengthInMinutes+" minutes)";
    }
}
