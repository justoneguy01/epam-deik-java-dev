package com.epam.training.ticketservice.ui.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ScreeningCommands {

    @ShellMethod(key = "create screening", value = "You can add screening with admin privilege.")
    public String createScreening() {
        return null;
    }
    @ShellMethod(key = "update screening", value = "You can update screening with admin privilege.")
    public String updateScreening() {
        return null;
    }
    @ShellMethod(key = "delete screening", value = "You can delete screening with admin privilege.")
    public String deleteScreening() {
        return null;
    }
    @ShellMethod(key = "list screenings", value = "You can list screenings.")
    public String listScreenings() {
        return null;
    }

}
