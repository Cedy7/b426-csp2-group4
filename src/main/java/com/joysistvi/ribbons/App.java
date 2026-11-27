package com.joysistvi.ribbons;

import com.joysistvi.ribbons.config.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        try (Connection conn = new DbConnection().connect()) {
            System.out.println("Connected to " + conn.getCatalog());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}