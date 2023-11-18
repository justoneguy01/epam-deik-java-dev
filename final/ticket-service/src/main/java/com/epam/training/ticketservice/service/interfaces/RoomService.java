package com.epam.training.ticketservice.service.interfaces;

import com.epam.training.ticketservice.model.entity.Room;

import java.util.List;

public interface RoomService {
    String createRoom(String roomName, int chairRow, int chairColumn);

    String updateRoom(String roomName, int chairRow, int chairColumn);

    String deleteRoom(String roomName);

    List<Room> listRooms();
}
