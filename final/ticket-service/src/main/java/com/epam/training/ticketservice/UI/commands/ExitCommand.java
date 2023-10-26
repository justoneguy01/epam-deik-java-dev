package com.epam.training.ticketservice.UI.commands;

import org.springframework.core.annotation.Order;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ExitCommand implements Quit.Command{
    @ShellMethod(key = "exit", value = "You can close the application with this command.")
    public String closeTheApplication(){
        System.exit(0);
        return "Exit command executed.";
    }
}
