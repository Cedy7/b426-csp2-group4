package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.model.enums.Role;
import com.joysistvi.ribbons.service.UserService;

import java.util.List;

public class UserView {

    private final UserService userService;

    public UserView(UserService userService) {
        this.userService = userService;
    }

    public void handleMenu(User currentUser) {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE USERS ===");
            System.out.println("1. View All Active Users");
            System.out.println("2. Add User");
            System.out.println("3. Update User");
            System.out.println("4. Reset Password");
            System.out.println("5. Search Users");
            System.out.println("6. Archive User");
            System.out.println("7. Restore Archived User");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllUsers();
                case 2 -> promptCreateUser();
                case 3 -> promptUpdateUser();
                case 4 -> promptResetPassword();
                case 5 -> promptSearchUsers();
                case 6 -> promptArchiveUser(currentUser);
                case 7 -> promptRestoreUser();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllUsers() {
        List<User> users = userService.getAllActiveUsers();

        System.out.println("\n----- Users -----");
        printUsers(users);
    }

    public void promptCreateUser() {
        System.out.println("\n----- Add User -----");
        String username = ConsoleInput.readString("Enter username: ");
        String email = ConsoleInput.readString("Enter email: ");
        String password = ConsoleInput.readPassword("Enter password: ");
        Role role = promptRole();

        boolean created = userService.createUser(username, email, password, role);

        if (created) {
            System.out.println("\nUser '" + username.trim() + "' created successfully!");
        } else {
            System.out.println("Failed to create user.");
        }
    }

    public void promptUpdateUser() {
        displayAllUsers();
        System.out.println("\n----- Update User -----");
        int id = ConsoleInput.readInt("Enter user ID to update: ");

        User user = userService.getUserById(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Note: Press Enter without typing anything to keep the current value.");
        String username = ConsoleInput.readString("Enter new username [" + user.getUsername() + "]: ");
        String email = ConsoleInput.readString("Enter new email [" + user.getEmail() + "]: ");
        System.out.println("Current role: " + user.getRole());
        String changeRole = ConsoleInput.readString("Change role? (Y/N) [N]: ");

        if (username.trim().isEmpty()) {
            username = user.getUsername();
        }

        if (email.trim().isEmpty()) {
            email = user.getEmail();
        }

        Role role = user.getRole();

        if (changeRole.trim().equalsIgnoreCase("Y")) {
            role = promptRole();
        }

        boolean updated = userService.updateUser(id, username, email, role);

        if (updated) {
            System.out.println("\nUser ID " + id + " updated successfully!");
            displayAllUsers();
        } else {
            System.out.println("Failed to update user.");
        }
    }

    public void promptResetPassword() {
        displayAllUsers();
        System.out.println("\n----- Reset Password -----");
        int id = ConsoleInput.readInt("Enter user ID: ");
        String newPassword = ConsoleInput.readPassword("Enter new password: ");
        String confirmPassword = ConsoleInput.readPassword("Confirm new password: ");

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("\nPasswords do not match.");
            return;
        }

        boolean reset = userService.resetPassword(id, newPassword);

        if (reset) {
            System.out.println("\nPassword for user ID " + id + " reset successfully!");
        } else {
            System.out.println("Failed to reset password.");
        }
    }

    public void promptSearchUsers() {
        System.out.println("\n----- Search Users -----");
        String keyword = ConsoleInput.readString("Enter keyword: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            userService.searchUsers(keyword);
            return;
        }

        List<User> users = userService.searchUsers(keyword);

        System.out.println("\n----- Search Users (Keyword: '" + keyword.trim() + "') -----");
        printUsers(users);
    }

    public void promptArchiveUser(User currentUser) {
        displayAllUsers();
        System.out.println("\n----- Archive User -----");
        int id = ConsoleInput.readInt("Enter user ID to archive: ");

        if (currentUser != null && id == currentUser.getId()) {
            System.out.println("\nYou cannot archive your own account.");
            return;
        }

        boolean archived = userService.archiveUser(id);

        if (archived) {
            System.out.println("\nUser ID " + id + " archived successfully!");
            displayAllUsers();
        } else {
            System.out.println("Failed to archive user.");
        }
    }

    public void promptRestoreUser() {
        System.out.println("\n----- Restore User -----");

        List<User> archivedUsers = userService.getAllArchivedUsers();

        if (archivedUsers.isEmpty()) {
            System.out.println("No archived users found.");
            return;
        }

        System.out.println("\n----- Archived Users -----");
        printUsers(archivedUsers);

        int id = ConsoleInput.readInt("Enter user ID to restore: ");

        boolean restored = userService.restoreUser(id);

        if (restored) {
            System.out.println("\nUser ID " + id + " restored successfully!");
            displayAllUsers();
        } else {
            System.out.println("Failed to restore user.");
        }
    }

    private Role promptRole() {
        while (true) {
            System.out.println("Role:");
            System.out.println("1. ADMIN");
            System.out.println("2. STAFF");
            int choice = ConsoleInput.readInt("Choose role: ");

            switch (choice) {
                case 1 -> {
                    return Role.ADMIN;
                }
                case 2 -> {
                    return Role.STAFF;
                }
                default -> System.out.println("Invalid role. Please try again.");
            }
        }
    }

    private void printUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        String border = ConsoleInput.border(5, 20, 30, 6);

        System.out.println(border);
        System.out.printf("| %-5s | %-20s | %-30s | %-6s |%n", "ID", "Username", "Email", "Role");
        System.out.println(border);

        for (User user : users) {
            System.out.printf("| %-5d | %-20s | %-30s | %-6s |%n",
                    user.getId(),
                    ConsoleInput.truncate(user.getUsername(), 20),
                    ConsoleInput.truncate(user.getEmail(), 30),
                    user.getRole());
        }

        System.out.println(border);
    }
}