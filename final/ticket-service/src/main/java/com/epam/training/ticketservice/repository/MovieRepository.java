package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByMovieTitle(String movieTitle);
}
