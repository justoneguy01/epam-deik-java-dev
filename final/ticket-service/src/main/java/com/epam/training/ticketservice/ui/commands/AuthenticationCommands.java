package com.epam.training.ticketservice.ui.commands;
import com.epam.training.ticketservice.dto.UserDTO;
import com.epam.training.ticketservice.model.User;
import com.epam.training.ticketservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class AuthenticationCommands {

    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "You can login with Admin account.")
    public String signIn(String username, String password) {
        return userService.login(username, password)
                .map(userDto -> userDto + " is successfully logged in!")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "sign up", value = "You can register a User account")
    public String registerUser(String userName, String password) {
        try {
            userService.registerUser(userName, password);
            return "Registration was successful!";
        } catch (Exception e) {
            return "Registration failed!";
        }
    }
    @ShellMethod(key = "sign in", value = "User login")
    public String login(String username, String password) {
        return userService.login(username, password)
                .map(userDto -> userDto.username() + " is successfully logged in!")
                .orElse("Login failed due to incorrect credentials");
    }
    @ShellMethod(key = "sign out", value = "You can sign out from your account.")
    public String logout() {
        return userService.logout()
                .map(userDto -> userDto.username() + " is logged out!")
                .orElse("You need to login first!");
    }
    @ShellMethod(key = "describe account", value = "Get account information")
    public String describe() {

        Optional<UserDTO> user = userService.describe();

        if (user.isPresent() && user.get().role() == User.Role.ADMIN){

            return "Signed in with privileged account '"+user.get().username()+"'";
        }
        else if (user.isPresent() && user.get().role() == User.Role.USER){

            return "Signed in with account '"+user.get().username()+"'";
        }
        else
        {
            return "You are not signed in";
        }
    }
}
