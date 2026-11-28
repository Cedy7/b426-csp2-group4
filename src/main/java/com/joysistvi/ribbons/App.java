package com.joysistvi.ribbons;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.model.User;
import com.joysistvi.ribbons.model.enums.Role;
import com.joysistvi.ribbons.repository.CategoryRepository;
import com.joysistvi.ribbons.repository.CategoryRepositoryImpl;
import com.joysistvi.ribbons.repository.CustomerRepository;
import com.joysistvi.ribbons.repository.CustomerRepositoryImpl;
import com.joysistvi.ribbons.repository.EventRepository;
import com.joysistvi.ribbons.repository.EventRepositoryImpl;
import com.joysistvi.ribbons.repository.SizeRepository;
import com.joysistvi.ribbons.repository.SizeRepositoryImpl;
import com.joysistvi.ribbons.repository.UserRepository;
import com.joysistvi.ribbons.repository.UserRepositoryImpl;
import com.joysistvi.ribbons.service.CategoryService;
import com.joysistvi.ribbons.service.CategoryServiceImpl;
import com.joysistvi.ribbons.service.CustomerService;
import com.joysistvi.ribbons.service.CustomerServiceImpl;
import com.joysistvi.ribbons.service.EventService;
import com.joysistvi.ribbons.service.EventServiceImpl;
import com.joysistvi.ribbons.service.SizeService;
import com.joysistvi.ribbons.service.SizeServiceImpl;
import com.joysistvi.ribbons.service.UserService;
import com.joysistvi.ribbons.service.UserServiceImpl;
import com.joysistvi.ribbons.view.*;

public class App {

    private final UserView userView;
    private final CustomerView customerView;
    private final CategoryView categoryView;
    private final SizeView sizeView;
    private final EventView eventView;
    private final LoginView loginView;

    public App() {
        DbConnection dbConnection = new DbConnection();

        UserRepository userRepository = new UserRepositoryImpl(dbConnection);
        UserService userService = new UserServiceImpl(userRepository);
        this.userView = new UserView(userService);

        this.loginView = new LoginView(userService);

        CustomerRepository customerRepository = new CustomerRepositoryImpl(dbConnection);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        this.customerView = new CustomerView(customerService);

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(dbConnection);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        this.categoryView = new CategoryView(categoryService);

        SizeRepository sizeRepository = new SizeRepositoryImpl(dbConnection);
        SizeService sizeService = new SizeServiceImpl(sizeRepository);
        this.sizeView = new SizeView(sizeService);

        EventRepository eventRepository = new EventRepositoryImpl(dbConnection);
        EventService eventService = new EventServiceImpl(eventRepository);
        this.eventView = new EventView(eventService);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    public void run() {
        while (true) {
            User currentUser = showAuthMenuAndAuthenticate();
            if (currentUser == null) {
                System.out.println("\nExiting Recording Studio App. Goodbye!");
                break;
            }
            System.out.println("\nWelcome, " + currentUser.getUsername() + " (" + currentUser.getRole() + ")!");
            if (Role.ADMIN == currentUser.getRole()) {
                adminDashboard(currentUser);
            } else {
                userDashboard(currentUser);
            }
            System.out.println("\nLogged out " + currentUser.getUsername() + ".");
        }
    }

    public void userDashboard(User currentUser) {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n==============================================");
            System.out.println("   RIBBONS - Lolita Rental Bridal Boutique");
            System.out.println("==============================================");
            System.out.println("1. Manage Customers");
//            System.out.println("2. Manage Rentals");
//            System.out.println("3. Manage Payments ");
//            System.out.println("4. Manage Returns");
//            System.out.println("5. Search Items");
            System.out.println("0. Logout");

            int choice = ConsoleInput.readInt("Choice: ");
            switch (choice) {
                case 1 -> userView.handleMenu(currentUser);
//                case 2 -> //Manage Rentals;
//                case 3 -> //Manage Payments;
//                case 4 -> //Manage Returns;
//                case 5 -> //Search Items;
                case 0 -> {
                    System.out.println("\nGoodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public void adminDashboard(User currentUser) {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n==============================================");
            System.out.println("   RIBBONS - Lolita Rental Bridal Boutique");
            System.out.println("==============================================");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Customers");
            System.out.println("3. Manage Categories");
            System.out.println("4. Manage Sizes");
            System.out.println("5. Manage Events");
            System.out.println("0. Logout");

            int choice = ConsoleInput.readInt("Choice: ");
            switch (choice) {
                case 1 -> userView.handleMenu(currentUser);
                case 2 -> customerView.handleMenu();
                case 3 -> categoryView.handleMenu();
                case 4 -> sizeView.handleMenu();
                case 5 -> eventView.handleMenu();
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

    public User showAuthMenuAndAuthenticate() {
        while (true) {
            System.out.println("\n=== RIBBONS APP ===");
            System.out.println("1. Login");
            System.out.println("2. Register Account");
            System.out.println("0. Exit");

            int choice = ConsoleInput.readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    User loggedInUser = loginView.promptLogin();
                    if (loggedInUser != null) {
                        return loggedInUser;
                    } else {
                        return null;
                    }
                case 2:
                    loginView.promptCreateRegularAccount();
                    break;
                case 0:
                    return null;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}