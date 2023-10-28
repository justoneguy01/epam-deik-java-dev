package com.epam.training.ticketservice.ui.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class RoomCommands {
    @ShellMethod(key = "create room", value = "You can add rooms with admin privilege.")
    public String createRoom() {
        return null;
    }
    @ShellMethod(key = "update room", value = "You can update rooms with admin privilege.")
    public String updateRoom() {
        return null;
    }
    @ShellMethod(key = "delete room", value = "You can delete rooms with admin privilege.")
    public String deleteRoom() {
        return null;
    }
    @ShellMethod(key = "list rooms", value = "You can list rooms.")
    public String listRooms() {
        return null;
    }
}
