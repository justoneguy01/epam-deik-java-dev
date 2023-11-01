package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.Movie;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    @Override
    public void createRoom(String roomName, int chairRow, int chairColumn) {
        try {
            Room room = new Room(roomName, chairRow, chairColumn);
            roomRepository.save(room);
        }catch (Exception e){
            throw new IllegalArgumentException("This room already exist.");
        }
    }

    @Override
    public void updateRoom(String roomName, int chairRow, int chairColumn) {
        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            room.setChairRow(chairRow);
            room.setChairColumn(chairColumn);
            room.setSeats(chairRow*chairColumn);
            roomRepository.save(room);
        } else {
            throw new IllegalArgumentException("Room not found");
        }
    }

    @Override
    public void deleteRoom(String roomName) {
        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            roomRepository.delete(room);
        } else {
            throw new IllegalArgumentException("Room not found");
        }
    }

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }
}
