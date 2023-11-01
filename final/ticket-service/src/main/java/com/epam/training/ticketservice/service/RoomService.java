package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;

import java.util.List;

public interface RoomService {
    void createRoom(String roomName, int chairRow, int chairColumn);

    void updateRoom(String roomName, int chairRow, int chairColumn);

    void deleteRoom(String roomName);

    List<Room> listRooms();
}
