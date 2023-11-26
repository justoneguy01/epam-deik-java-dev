package com.epam.training.ticketservice.service.interfaces;


public interface RoomService {
    String createRoom(String roomName, int chairRow, int chairColumn);

    String updateRoom(String roomName, int chairRow, int chairColumn);

    String deleteRoom(String roomName);

    String listRooms();
}
