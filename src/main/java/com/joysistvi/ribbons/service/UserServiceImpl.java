package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.model.enums.Role;
import com.joysistvi.ribbons.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }

    @Override
    public List<User> getAllArchivedUsers() {
        return userRepository.findAllArchived();
    }

    @Override
    public User getUserById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return userRepository.findById(id);
    }

    @Override
    public boolean createUser(String username, String email, String rawPassword, Role role) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("\nValidation Error: Username is required.");
            return false;
        }

        if (!isValidEmail(email)) {
            System.out.println("\nValidation Error: Valid email is required.");
            return false;
        }

        if (rawPassword == null || rawPassword.trim().length() < 6) {
            System.out.println("\nValidation Error: Password must be at least 6 characters.");
            return false;
        }

        if (role == null) {
            System.out.println("\nValidation Error: Role is required.");
            return false;
        }

        if (userRepository.findByUsername(username.trim()) != null) {
            System.out.println("\nValidation Error: Username already exists.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(rawPassword.trim(), BCrypt.gensalt());

        User user = new User(username.trim(), email.trim(), hashedPassword, role);
        return userRepository.save(user);
    }

    @Override // created specifically for CREATE / REGISTER User menu so that anyone can't be admin in their own accord
    public boolean createRegularAccount(String username, String email, String rawPassword) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("\nValidation Error: Username is required.");
            return false;
        }

        if (!isValidEmail(email)) {
            System.out.println("\nValidation Error: Valid email is required.");
            return false;
        }

        if (rawPassword == null || rawPassword.trim().length() < 6) {
            System.out.println("\nValidation Error: Password must be at least 6 characters.");
            return false;
        }

        if (userRepository.findByUsername(username.trim()) != null) {
            System.out.println("\nValidation Error: Username already exists.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(rawPassword.trim(), BCrypt.gensalt());

        User user = new User(username.trim(), email.trim(), hashedPassword);
        return userRepository.saveRegularAccount(user);
    }


    @Override
    public boolean updateUser(int id, String username, String email, Role role) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (username == null || username.trim().isEmpty()) {
            System.out.println("\nValidation Error: Username is required.");
            return false;
        }

        if (!isValidEmail(email)) {
            System.out.println("\nValidation Error: Valid email is required.");
            return false;
        }

        if (role == null) {
            System.out.println("\nValidation Error: Role is required.");
            return false;
        }

        User existing = userRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: User not found.");
            return false;
        }

        existing.setUsername(username.trim());
        existing.setEmail(email.trim());
        existing.setRole(role);
        return userRepository.update(existing);
    }

    @Override
    public boolean resetPassword(int id, String newPassword) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (newPassword == null || newPassword.trim().length() < 6) {
            System.out.println("\nValidation Error: Password must be at least 6 characters.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(newPassword.trim(), BCrypt.gensalt());
        return userRepository.updatePassword(id, hashedPassword);
    }

    @Override
    public boolean archiveUser(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return userRepository.archiveById(id);
    }

    @Override
    public boolean restoreUser(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return userRepository.restoreById(id);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("\nValidation Error: Search keyword is required.");
            return new ArrayList<>();
        }

        return userRepository.searchByUsername(keyword.trim());
    }

    @Override
    public User loginUser(String username, String candidatePassword) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("\nValidation Error: Username is required.");
            return null;
        }

        if (candidatePassword == null || candidatePassword.isEmpty()) {
            System.out.println("\nValidation Error: Password is required.");
            return null;
        }

        User user = userRepository.findByUsername(username.trim());

        if (user == null) {
            return null;
        }

        try {
            if (BCrypt.checkpw(candidatePassword, user.getPasswordHash())) {
                return user;
            }
        } catch (IllegalArgumentException e) {
            return null;
        }

        return null;
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() && email.contains("@") && email.contains(".");
    }
}