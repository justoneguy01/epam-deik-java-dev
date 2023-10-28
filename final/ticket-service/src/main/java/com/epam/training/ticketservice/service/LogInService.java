package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.model.user_models.AdminModel;
import org.springframework.stereotype.Service;
@Service
public class LogInService {
    private AdminModel adminUserModel;
    public LogInService(AdminModel adminUserModel) {
        this.adminUserModel = adminUserModel;
    }
    public boolean login(String username, String password) {
        if (adminUserModel.getUsername().equals(username) && adminUserModel.getPassword().equals(password)) {
            adminUserModel.setLoggedIn(true);
            return true;
        }
        return false;
    }
    public void logout() {
        adminUserModel.setLoggedIn(false);
    }
    public boolean isLoggedIn() {
        return adminUserModel.isLoggedIn();
    }
}
