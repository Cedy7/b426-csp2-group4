package com.joysistvi.ribbons;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.repository.CustomerRepository;
import com.joysistvi.ribbons.repository.CustomerRepositoryImpl;
import com.joysistvi.ribbons.repository.UserRepository;
import com.joysistvi.ribbons.repository.UserRepositoryImpl;
import com.joysistvi.ribbons.service.CustomerService;
import com.joysistvi.ribbons.service.CustomerServiceImpl;
import com.joysistvi.ribbons.service.UserService;
import com.joysistvi.ribbons.service.UserServiceImpl;
import com.joysistvi.ribbons.view.ConsoleInput;
import com.joysistvi.ribbons.view.CustomerView;
import com.joysistvi.ribbons.view.UserView;

public class App {

    private final UserView userView;
    private final CustomerView customerView;

    public App() {
        DbConnection dbConnection = new DbConnection();

        UserRepository userRepository = new UserRepositoryImpl(dbConnection);
        UserService userService = new UserServiceImpl(userRepository);
        this.userView = new UserView(userService);

        CustomerRepository customerRepository = new CustomerRepositoryImpl(dbConnection);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        this.customerView = new CustomerView(customerService);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n==============================================");
            System.out.println("   RIBBONS - Lolita Rental Bridal Boutique");
            System.out.println("==============================================");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Customers");
            System.out.println("0. Exit");

            int choice = ConsoleInput.readInt("Choice: ");
            switch (choice) {
                case 1 -> userView.handleMenu(null);
                case 2 -> customerView.handleMenu();
                case 0 -> {
                    System.out.println("\nGoodbye!");
                    return;
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    ConsoleInput.pause();
                }
            }
        }
    }
}