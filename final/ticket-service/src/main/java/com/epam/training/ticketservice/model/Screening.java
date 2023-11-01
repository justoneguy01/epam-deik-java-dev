package com.epam.training.ticketservice.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Screening")
@Data
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String movieTitle;
    private String roomName;
    private LocalDateTime beginScreening;

    public Screening(String movieTitle, String roomName, LocalDateTime beginScreening) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.beginScreening = beginScreening;
    }

    @Override
    public String toString() {
       return  movieTitle + " " + roomName + " " + beginScreening;
    }
}
