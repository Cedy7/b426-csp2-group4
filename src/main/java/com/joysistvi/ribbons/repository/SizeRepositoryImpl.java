package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Size;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SizeRepositoryImpl implements SizeRepository {

    private final DbConnection dbConnection;

    public SizeRepositoryImpl(DbConnection dbConnection) { this.dbConnection = dbConnection; }

    @Override
    public Size findById(int id) {
        String query = "SELECT * FROM sizes WHERE id = ?";

        try (Connection connection = dbConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Size(resultSet.getInt("id"),
                            resultSet.getString("code"),
                            resultSet.getInt("sort_order")
                            );
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Size By Id Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Size> findAllAvailableSize() {
        List<Size> sizes = new ArrayList<>();
        String query = "SELECT * FROM sizes ORDER BY sort_order DESC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
             sizes.add(new Size(
                     resultSet.getInt("id"),
                     resultSet.getString("code"),
                     resultSet.getInt("sort_order")));
            }
        } catch (SQLException e) {
            System.out.println("\nRead All Sizes Error: " + e.getMessage());
        }

        return sizes;
    }

    @Override
    public List<Size> findBySortorder(int sortOrder) {
        List<Size> sizes = new ArrayList<>();
        String query = "SELECT * FROM sizes WHERE sort_order = ?";

        try (Connection connection = dbConnection.connect();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, sortOrder);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    sizes.add(new Size(
                            resultSet.getString("code"),
                            resultSet.getInt("sortOrder")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Find Size By Order Error: " + e.getMessage());
        }

        return sizes;
    }
}
