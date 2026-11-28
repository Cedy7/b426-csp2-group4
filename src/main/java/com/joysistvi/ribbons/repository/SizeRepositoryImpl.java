package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Size;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SizeRepositoryImpl implements SizeRepository {

    private final DbConnection dbConnection;

    public SizeRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Size> findAll() {
        List<Size> sizes = new ArrayList<>();
        String query = "SELECT * FROM sizes ORDER BY sort_order ASC, id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                sizes.add(mapSize(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Sizes Error: " + e.getMessage());
        }

        return sizes;
    }

    @Override
    public Size findById(int id) {
        String query = "SELECT * FROM sizes WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSize(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Size Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Size findByCode(String code) {
        String query = "SELECT * FROM sizes WHERE code = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapSize(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Size Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(Size size) {
        String query = "INSERT INTO sizes (code, sort_order) VALUES (?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, size.getCode());
            preparedStatement.setInt(2, size.getSortOrder());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate Size Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Size size) {
        String query = "UPDATE sizes SET code = ?, sort_order = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, size.getCode());
            preparedStatement.setInt(2, size.getSortOrder());
            preparedStatement.setInt(3, size.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Size Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM sizes WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("\nDelete Size Error: This size is used by one or more items and cannot be deleted.");
            return false;
        } catch (SQLException e) {
            System.out.println("\nDelete Size Error: " + e.getMessage());
            return false;
        }
    }

    private Size mapSize(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String code = resultSet.getString("code");
        int sortOrder = resultSet.getInt("sort_order");
        return new Size(id, code, sortOrder);
    }
}