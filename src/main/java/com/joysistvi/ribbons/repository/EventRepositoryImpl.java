package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepositoryImpl implements EventRepository {

    private final DbConnection dbConnection;

    public EventRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Event> findAllActive() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE is_archived = 0 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                events.add(mapEvent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Events Error: " + e.getMessage());
        }

        return events;
    }

    @Override
    public List<Event> findAllArchived() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE is_archived = 1 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                events.add(mapEvent(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Archived Events Error: " + e.getMessage());
        }

        return events;
    }

    @Override
    public Event findById(int id) {
        String query = "SELECT * FROM events WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Event Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Event findByName(String name) {
        String query = "SELECT * FROM events WHERE name = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapEvent(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Event Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(Event event) {
        String query = "INSERT INTO events (name) VALUES (?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, event.getName());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate Event Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Event event) {
        String query = "UPDATE events SET name = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, event.getName());
            preparedStatement.setInt(2, event.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Event Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE events SET is_archived = 1 WHERE id = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nArchive Event Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE events SET is_archived = 0 WHERE id = ? AND is_archived = 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nRestore Event Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Event> searchByName(String keyword) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM events WHERE is_archived = 0 AND name LIKE ? ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + keyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    events.add(mapEvent(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nSearch Event Error: " + e.getMessage());
        }

        return events;
    }

    private Event mapEvent(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        boolean archived = resultSet.getBoolean("is_archived");
        return new Event(id, name, archived);
    }
}