package com.epam.training.ticketservice.service.implementation;

import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.model.repository.RoomRepository;
import com.epam.training.ticketservice.service.interfaces.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public String createRoom(String roomName, int chairRow, int chairColumn) {

        if (roomRepository.findByRoomName(roomName).isPresent()) {
            throw new IllegalArgumentException("This room is already exist!");
        } else {
            Room room = new Room(roomName, chairRow, chairColumn);
            roomRepository.save(room);
            return roomName + " successfully added";
        }
    }

    @Override
    public String updateRoom(String roomName, int chairRow, int chairColumn) {

        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            room.setChairRow(chairRow);
            room.setChairColumn(chairColumn);
            room.setSeats(chairRow * chairColumn);
            roomRepository.save(room);
            return roomName + " successfully updated";
        } else {
            throw new IllegalArgumentException("The " + roomName + " is not found!");
        }
    }

    @Override
    public String deleteRoom(String roomName) {

        if (roomRepository.findByRoomName(roomName).isPresent()) {
            Room room = roomRepository.findByRoomName(roomName).get();
            roomRepository.delete(room);
            return roomName + " successfully deleted";
        } else {
            throw new IllegalArgumentException("The " + roomName + " is not found!");
        }
    }

    @Override
    public String listRooms() {

        List<Room> rooms = roomRepository.findAll();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            String roomString = "";
            for (Room room : rooms) {
                roomString += room.toString() + "\n";
            }
            return roomString;
        }
    }
}
