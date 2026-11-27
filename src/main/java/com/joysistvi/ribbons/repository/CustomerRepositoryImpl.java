package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {

    private final DbConnection dbConnection;

    public CustomerRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Customer> findAllActive() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE is_archived = 0 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Customers Error: " + e.getMessage());
        }

        return customers;
    }

    @Override
    public List<Customer> findAllArchived() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE is_archived = 1 ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("\nRead Archived Customers Error: " + e.getMessage());
        }

        return customers;
    }

    @Override
    public Customer findById(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Customer Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        String query = "SELECT * FROM customers WHERE phone_number = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, phoneNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Customer Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        String query = "SELECT * FROM customers WHERE email = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println("\nFind Customer Error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean save(Customer customer) {
        String query = "INSERT INTO customers (first_name, last_name, phone_number, email, address) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nCreate Customer Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Customer customer) {
        String query = "UPDATE customers SET first_name = ?, last_name = ?, phone_number = ?, email = ?, address = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getAddress());
            preparedStatement.setInt(6, customer.getId());

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nUpdate Customer Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE customers SET is_archived = 1 WHERE id = ? AND is_archived = 0";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nArchive Customer Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE customers SET is_archived = 0 WHERE id = ? AND is_archived = 1";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("\nRestore Customer Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Customer> searchByNameOrPhone(String keyword) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE is_archived = 0 AND "
                + "(first_name LIKE ? OR last_name LIKE ? OR CONCAT(first_name, ' ', last_name) LIKE ? OR phone_number LIKE ?) "
                + "ORDER BY id ASC";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            String pattern = "%" + keyword + "%";
            preparedStatement.setString(1, pattern);
            preparedStatement.setString(2, pattern);
            preparedStatement.setString(3, pattern);
            preparedStatement.setString(4, pattern);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    customers.add(mapCustomer(resultSet));
                }
            }
        } catch (SQLException e) {
            System.out.println("\nSearch Customer Error: " + e.getMessage());
        }

        return customers;
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String phoneNumber = resultSet.getString("phone_number");
        String email = resultSet.getString("email");
        String address = resultSet.getString("address");
        boolean archived = resultSet.getBoolean("is_archived");
        return new Customer(id, firstName, lastName, phoneNumber, email, address, archived);
    }
}