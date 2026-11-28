package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Item;
import com.joysistvi.ribbons.model.enums.Gender;
import com.joysistvi.ribbons.model.enums.ItemStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {

    private static final String SELECT_ITEMS = "SELECT i.*, c.name AS category_name, s.code AS size_code "
            + "FROM items i "
            + "JOIN categories c ON i.category_id = c.id "
            + "JOIN sizes s ON i.size_id = s.id ";

    private final DbConnection dbConnection;

    public ItemRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Item> findAllActive() {
        List<Item> items = new ArrayList<>();
        String query = SELECT_ITEMS + "WHERE i.is_archived = 0 ORDER BY i.sku ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                items.add(mapItem(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Items Error: " + e.getMessage());
        }

        return items;
    }

    @Override
    public List<Item> findAllArchived() {
        List<Item> items = new ArrayList<>();
        String query = SELECT_ITEMS + "WHERE i.is_archived = 1 ORDER BY i.sku ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                items.add(mapItem(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Archived Items Error: " + e.getMessage());
        }

        return items;
    }

    @Override
    public List<Item> findByStatus(ItemStatus status) {
        List<Item> items = new ArrayList<>();
        String query = SELECT_ITEMS + "WHERE i.is_archived = 0 AND i.status = ? ORDER BY i.sku ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(mapItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nRead Items By Status Error: " + e.getMessage());
        }

        return items;
    }

    @Override
    public Item findById(int id) {
        String query = SELECT_ITEMS + "WHERE i.id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapItem(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Item Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Item findBySku(String sku) {
        String query = SELECT_ITEMS + "WHERE i.sku = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, sku);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapItem(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Item Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String findLastSkuByPrefix(String prefix) {
        String query = "SELECT sku FROM items WHERE sku LIKE ? ORDER BY sku DESC LIMIT 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, prefix + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("sku");
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Last SKU Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(Item item) {
        String query = "INSERT INTO items (sku, category_id, size_id, name, color, gender, rental_price, replacement_cost) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, item.getSku());
            preparedStatement.setInt(2, item.getCategoryId());
            preparedStatement.setInt(3, item.getSizeId());
            preparedStatement.setString(4, item.getName());
            preparedStatement.setString(5, item.getColor());
            preparedStatement.setString(6, item.getGender().name());
            preparedStatement.setBigDecimal(7, item.getRentalPrice());
            preparedStatement.setBigDecimal(8, item.getReplacementCost());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate Item Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Item item) {
        String query = "UPDATE items SET size_id = ?, name = ?, color = ?, gender = ?, rental_price = ?, replacement_cost = ? "
                + "WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, item.getSizeId());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setString(3, item.getColor());
            preparedStatement.setString(4, item.getGender().name());
            preparedStatement.setBigDecimal(5, item.getRentalPrice());
            preparedStatement.setBigDecimal(6, item.getReplacementCost());
            preparedStatement.setInt(7, item.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Item Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int id, ItemStatus status) {
        String query = "UPDATE items SET status = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Item Status Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE items SET is_archived = 1 WHERE id = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nArchive Item Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE items SET is_archived = 0 WHERE id = ? AND is_archived = 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nRestore Item Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Item> searchByKeyword(String keyword) {
        List<Item> items = new ArrayList<>();
        String query = SELECT_ITEMS + "WHERE i.is_archived = 0 AND "
                + "(i.sku LIKE ? OR i.name LIKE ? OR i.color LIKE ? OR c.name LIKE ?) "
                + "ORDER BY i.sku ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String pattern = "%" + keyword + "%";
            preparedStatement.setString(1, pattern);
            preparedStatement.setString(2, pattern);
            preparedStatement.setString(3, pattern);
            preparedStatement.setString(4, pattern);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(mapItem(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nSearch Item Error: " + e.getMessage());
        }

        return items;
    }

    private Item mapItem(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String sku = resultSet.getString("sku");
        int categoryId = resultSet.getInt("category_id");
        int sizeId = resultSet.getInt("size_id");
        String name = resultSet.getString("name");
        String color = resultSet.getString("color");
        Gender gender = Gender.valueOf(resultSet.getString("gender"));
        BigDecimal rentalPrice = resultSet.getBigDecimal("rental_price");
        BigDecimal replacementCost = resultSet.getBigDecimal("replacement_cost");
        ItemStatus status = ItemStatus.valueOf(resultSet.getString("status"));
        boolean archived = resultSet.getBoolean("is_archived");

        Item item = new Item(id, sku, categoryId, sizeId, name, color, gender, rentalPrice, replacementCost, status, archived);
        item.setCategoryName(resultSet.getString("category_name"));
        item.setSizeCode(resultSet.getString("size_code"));
        return item;
    }
}