package com.epam.training.ticketservice.ui.commands;

import com.epam.training.ticketservice.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class AuthenticationCommands {
    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "You can login with Admin account.")
    public String signInPrivileged(String username, String password) {
        return userService.signInPrivileged(username,password);
    }

    @ShellMethod(key = "sign out", value = "You can sign out from your account.")
    public String logout() {
        return userService.signOut();
    }

    @ShellMethod(key = "describe account", value = "Get account information")
    public String describe() {
        return userService.describe();
    }
}