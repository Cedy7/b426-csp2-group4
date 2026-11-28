package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.service.UserService;

public class LoginView {

    private final UserService userService;

    public LoginView(UserService userService) {
        this.userService = userService;
    }

    public User promptLogin() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("\n=== LOGIN ===");
            String username = ConsoleInput.readString("Enter username: ");
            String password = ConsoleInput.readString("Enter password (min 6 chars) ");

            User loggedIn = userService.loginUser(username, password);
            if (loggedIn != null) {
                System.out.println("Login successful! Welcome, " + loggedIn.getUsername() + " (" + loggedIn.getRole() + ").");
                return loggedIn;
            }
            System.out.println("Login failed: Invalid username or password.");
            attempts++;
        }
        System.out.println("Warning: Maximum of 3 login attempts is allowed");
        return null;
    }

    public void promptCreateRegularAccount() {
        System.out.println("\n=== CREATE / REGISTER User ===");
        String username = ConsoleInput.readString("Enter username: ");
        String email = ConsoleInput.readString("Enter email: ");
        String rawPassword = ConsoleInput.readString("Enter password (min 6 chars): ");

        boolean created = userService.createRegularAccount(username, email, rawPassword);
        if (created) {
            System.out.println("User '" + username.trim() + "' registered successfully!");
        } else {
            System.out.println("Failed to create/register user.");
        }
    }
}
