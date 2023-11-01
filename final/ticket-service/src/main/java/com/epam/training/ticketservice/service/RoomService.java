package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Room;

import java.util.List;

public interface RoomService {
    String createRoom(String roomName, int chairRow, int chairColumn);

    String updateRoom(String roomName, int chairRow, int chairColumn);

    String deleteRoom(String roomName);

    List<Room> listRooms();
}
