package com.epam.training.ticketservice.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class MovieContainer {
    private List<Movie> movies;
}
