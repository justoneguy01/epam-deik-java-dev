package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.model.entity.Room;
import com.epam.training.ticketservice.service.interfaces.RoomService;
import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import java.util.List;

@ShellComponent
@AllArgsConstructor
public class RoomCommands {

    private final UserService userService;
    private final RoomService roomService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "You can add rooms with admin privilege.")
    public String createRoom(String roomName, int chairRow, int chairColumn) {
        roomService.createRoom(roomName, chairRow, chairColumn);
        return "Room added successfully!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "You can update rooms with admin privilege.")
    public String updateRoom(String roomName, int chairRow, int chairColumn) {
        roomService.updateRoom(roomName, chairRow, chairColumn);
        return roomName + " updated successfully";
    }

    @ShellMethodAvailability("isAvailable")
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
        } else {
            String roomString = "";
            for (Room room : rooms) {
                roomString += room.toString() + "\n";
            }
            return roomString;
        }
    }

    private Availability isAvailable() {
        return userService.getLoggedUser().getUsername() == "admin"
                ? Availability.available()
                : Availability.unavailable("You are not authorized");
    }
}
