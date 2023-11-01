package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Movie")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String movieTitle;
    private String genre;
    private int lengthInMinutes;

    public Movie(String movieTitle, String genre, int lengthInMinutes) {
        this.movieTitle = movieTitle;
        this.genre = genre;
        this.lengthInMinutes = lengthInMinutes;
    }
    @Override
    public String toString() {
        return  movieTitle + " ("+genre+", "+lengthInMinutes+" minutes)";
    }
}
