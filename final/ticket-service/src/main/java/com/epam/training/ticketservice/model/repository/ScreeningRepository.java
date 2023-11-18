package com.epam.training.ticketservice.model.repository;

import com.epam.training.ticketservice.model.entity.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    Optional<Screening> findByMovieTitleAndRoomNameAndBeginScreening(
            String movieTitle, String roomName, LocalDateTime beginScreening);

    Optional<Screening> findByRoomName(String roomName);
}