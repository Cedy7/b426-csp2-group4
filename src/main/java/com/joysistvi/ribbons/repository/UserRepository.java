package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllActive();

    List<User> findAllArchived();

    User findById(int id);

    User findByUsername(String username);

    boolean save(User user);

    // created specifically for CREATE / REGISTER User menu so that anyone can't be admin in their own accord
    boolean saveRegularAccount(User user);

    boolean update(User user);

    boolean updatePassword(int id, String passwordHash);

    boolean archiveById(int id);

    boolean restoreById(int id);

    List<User> searchByUsername(String keyword);
}