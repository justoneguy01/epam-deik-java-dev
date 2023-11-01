package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommands {
    private final UserService userService;
    private final RoomService roomService;
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "You can add rooms with admin privilege.")
    public String createRoom(String roomName, int chairRow, int chairColumn) {
        roomService.createRoom(roomName, chairRow, chairColumn);
        return "Room added successfully!";
    }
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "You can update rooms with admin privilege.")
    public String updateRoom(String roomName, int chairRow, int chairColumn) {
        roomService.updateRoom(roomName, chairRow, chairColumn);
        return roomName + " updated successfully";
    }
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "You can delete rooms with admin privilege.")
    public String deleteRoom(String roomName) {
        roomService.deleteRoom(roomName);
        return roomName + " deleted successfully";
    }
    @ShellMethod(key = "list rooms", value = "You can list rooms.")
    public String listRooms() {
        List<Room> rooms = roomService.listRooms();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }else {return rooms.toString();}
    }
    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
