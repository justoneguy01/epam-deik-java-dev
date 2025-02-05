package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.service.interfaces.ScreeningService;
import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {
    private  final UserService userService;
    private  final ScreeningService screeningService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "You can add screening with admin privilege.")
    public String createScreening(String movieTitle, String roomName, String beginScreening) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return screeningService.createScreening(movieTitle, roomName, LocalDateTime.parse(beginScreening, formatter));
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "You can delete screening with admin privilege.")
    public String deleteScreening(String movieTitle, String roomName, String beginScreening) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return screeningService.deleteScreening(movieTitle, roomName, LocalDateTime.parse(beginScreening, formatter));
    }

    @ShellMethod(key = "list screenings", value = "You can list screenings.")
    public String listScreenings() {
        return screeningService.listScreenings();
    }

    private Availability isAvailable() {
        return userService.getLoggedUser().getUsername().equals("admin")
                ? Availability.available()
                : Availability.unavailable("You are not authorized");
    }
}
