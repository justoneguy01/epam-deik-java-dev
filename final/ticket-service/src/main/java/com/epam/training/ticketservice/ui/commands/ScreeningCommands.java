package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.Room;
import com.epam.training.ticketservice.model.Screening;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.service.ScreeningServiceImpl;
import com.epam.training.ticketservice.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class ScreeningCommands {
    private  final UserServiceImpl userService;
    private  final ScreeningServiceImpl screeningService;
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "You can add screening with admin privilege.")
    public String createScreening(String movieTitle, String roomName, String beginScreening) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return screeningService.createScreening(movieTitle, roomName, LocalDateTime.parse(beginScreening, formatter));
    }
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "You can delete screening with admin privilege.")
    public String deleteScreening(String movieTitle, String roomName, String beginScreening) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return screeningService.deleteScreening(movieTitle, roomName, LocalDateTime.parse(beginScreening, formatter));
    }
    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "list screenings", value = "You can list screenings.")
    public String listScreenings() {
        List<Screening> screenings = screeningService.listScreenings();
        if (screenings.isEmpty()) {
            return "There are no screenings at the moment";
        }else {return screenings.toString();}
    }
    private Availability isAvailable() {
        Optional<UserDTO> user = userService.describe();
        return user.isPresent() && user.get().role() == User.Role.ADMIN ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
