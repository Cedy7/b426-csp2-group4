package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.model.enums.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final DbConnection dbConnection;

    public UserRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> findAllActive(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE is_archived = 0 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while(resultSet.next()){
                users.add(mapUser(resultSet));
            }
        }catch (SQLException e){
            System.out.println("\nRead Users Error: " + e.getMessage());
        }

        return users;
    }

    @Override
    public List<User> findAllArchived(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE is_archived = 1 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ){
            while(resultSet.next()){
                users.add(mapUser(resultSet));
            }
        }catch (SQLException e){
            System.out.println("\nRead Archived Users Error: " + e.getMessage());
        }

        return users;
    }

    @Override
    public User findById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind User Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public User findByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapUser(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind User Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(User user) {
        String query = "INSERT INTO users (username, email, password_hash, role) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.setString(4, user.getRole().name());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate User Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        String query = "UPDATE users SET username = ?, email = ?, role = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getRole().name());
            preparedStatement.setInt(4, user.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate User Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePassword(int id, String passwordHash) {
        String query = "UPDATE users SET password_hash = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, passwordHash);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Password Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE users SET is_archived = 1 WHERE id = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nArchive User Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE users SET is_archived = 0 WHERE id = ? AND is_archived = 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nRestore User Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<User> searchByUsername(String keyword) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE is_archived = 0 AND username LIKE ? ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    users.add(mapUser(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nSearch User Error: " + e.getMessage());
        }

        return users;
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String email = resultSet.getString("email");
        String passwordHash = resultSet.getString("password_hash");
        Role role = Role.valueOf(resultSet.getString("role"));
        boolean archived = resultSet.getBoolean("is_archived");
        return new User(id, username, email, passwordHash, role, archived);
    }
}