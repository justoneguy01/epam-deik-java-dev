package com.epam.training.ticketservice.model.user_models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdminModel {
    private final String username = "admin";
    private final String password = "admin";
    private final String accountType = "admin";
    private boolean isLoggedIn = false;
}
