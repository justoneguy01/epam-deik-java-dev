package com.epam.training.ticketservice.model.repository;

import com.epam.training.ticketservice.model.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByMovieTitle(String movieTitle);
}
