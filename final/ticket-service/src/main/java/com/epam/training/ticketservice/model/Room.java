package com.epam.training.ticketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Room")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String roomName;
    private int chairRow;
    private int chairColumn;
    private int seats;

    public Room(String roomName, int chairRow, int chairColumn) {
        this.roomName = roomName;
        this.chairRow = chairRow;
        this.chairColumn = chairColumn;
        this.seats = chairRow*chairColumn;
    }
    public String toString() {
        return  "Room " + roomName + " with " + seats + " seats, " + chairRow +" rows and "+ chairColumn +" columns";
    }
}
