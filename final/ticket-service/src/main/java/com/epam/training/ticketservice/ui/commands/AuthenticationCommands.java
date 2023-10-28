package com.epam.training.ticketservice.ui.commands;
import com.epam.training.ticketservice.model.user_models.AdminModel;
import com.epam.training.ticketservice.service.LogInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthenticationCommands {
    private AdminModel adminUserModel;
    private LogInService logInService;

    @Autowired
    public AuthenticationCommands(AdminModel adminUserModel, LogInService logInService) {
        this.adminUserModel = adminUserModel;
        this.logInService = logInService;
    }

    @ShellMethod(key = "sign in privileged", value = "You can login with Admin account.")
    public String signIn(String username, String password) {

        if (adminUserModel.isLoggedIn()) {
            return "Already logged in.";
        }

        if (logInService.login(username, password)) {
            return "Login successfully.";
        } else {
            return "Login failed due to incorrect credentials.";
        }
    }

    @ShellMethod(key = "sign out", value = "You can sign out from your account.")
    public String signOut() {
        if (adminUserModel.isLoggedIn()) {
            logInService.logout();
            return "Logged out successfully.";
        } else {
            return "No user is currently signed in.";
        }
    }
}
