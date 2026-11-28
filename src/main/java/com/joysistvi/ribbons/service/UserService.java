package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.model.enums.Role;

import java.util.List;

public interface UserService {

    List<User> getAllActiveUsers();

    List<User> getAllArchivedUsers();

    User getUserById(int id);

    boolean createUser(String username, String email, String rawPassword, Role role);

    // created specifically for CREATE / REGISTER User menu so that anyone can't be admin in their own accord
    boolean createRegularAccount(String username, String email, String rawPassword);

    boolean updateUser(int id, String username, String email, Role role);

    boolean resetPassword(int id, String newPassword);

    boolean archiveUser(int id);

    boolean restoreUser(int id);

    List<User> searchUsers(String keyword);

    User loginUser(String username, String candidatePassword);
}