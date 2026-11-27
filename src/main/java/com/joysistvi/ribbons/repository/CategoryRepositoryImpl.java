package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final DbConnection dbConnection;

    public CategoryRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Category> findAllActive() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE is_archived = 0 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                categories.add(mapCategory(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Categories Error: " + e.getMessage());
        }

        return categories;
    }

    @Override
    public List<Category> findAllArchived() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE is_archived = 1 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                categories.add(mapCategory(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Archived Categories Error: " + e.getMessage());
        }

        return categories;
    }

    @Override
    public Category findById(int id) {
        String query = "SELECT * FROM categories WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Category Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Category findByCode(String code) {
        String query = "SELECT * FROM categories WHERE code = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Category Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Category findByName(String name) {
        String query = "SELECT * FROM categories WHERE name = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCategory(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Category Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(Category category) {
        String query = "INSERT INTO categories (code, name, description) VALUES (?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getCode());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getDescription());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate Category Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Category category) {
        String query = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Category Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE categories SET is_archived = 1 WHERE id = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nArchive Category Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE categories SET is_archived = 0 WHERE id = ? AND is_archived = 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nRestore Category Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Category> searchByNameOrCode(String keyword) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE is_archived = 0 AND (name LIKE ? OR code LIKE ?) ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String pattern = "%" + keyword + "%";
            preparedStatement.setString(1, pattern);
            preparedStatement.setString(2, pattern);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    categories.add(mapCategory(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nSearch Category Error: " + e.getMessage());
        }

        return categories;
    }

    private Category mapCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String code = resultSet.getString("code");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        boolean archived = resultSet.getBoolean("is_archived");
        return new Category(id, code, name, description, archived);
    }
}