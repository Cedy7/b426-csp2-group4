package com.joysistvi.ribbons;

import com.joysistvi.ribbons.config.DbConnection;
import com.joysistvi.ribbons.repository.CategoryRepository;
import com.joysistvi.ribbons.repository.CategoryRepositoryImpl;
import com.joysistvi.ribbons.repository.CustomerRepository;
import com.joysistvi.ribbons.repository.CustomerRepositoryImpl;
import com.joysistvi.ribbons.repository.EventRepository;
import com.joysistvi.ribbons.repository.EventRepositoryImpl;
import com.joysistvi.ribbons.repository.ItemRepository;
import com.joysistvi.ribbons.repository.ItemRepositoryImpl;
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
import com.joysistvi.ribbons.service.ItemService;
import com.joysistvi.ribbons.service.ItemServiceImpl;
import com.joysistvi.ribbons.service.SizeService;
import com.joysistvi.ribbons.service.SizeServiceImpl;
import com.joysistvi.ribbons.service.UserService;
import com.joysistvi.ribbons.service.UserServiceImpl;
import com.joysistvi.ribbons.view.CategoryView;
import com.joysistvi.ribbons.view.ConsoleInput;
import com.joysistvi.ribbons.view.CustomerView;
import com.joysistvi.ribbons.view.EventView;
import com.joysistvi.ribbons.view.ItemView;
import com.joysistvi.ribbons.view.SizeView;
import com.joysistvi.ribbons.view.UserView;

public class App {

    private final UserView userView;
    private final CustomerView customerView;
    private final CategoryView categoryView;
    private final SizeView sizeView;
    private final EventView eventView;
    private final ItemView itemView;

    public App() {
        DbConnection dbConnection = new DbConnection();

        UserRepository userRepository = new UserRepositoryImpl(dbConnection);
        UserService userService = new UserServiceImpl(userRepository);
        this.userView = new UserView(userService);

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

        ItemRepository itemRepository = new ItemRepositoryImpl(dbConnection);
        ItemService itemService = new ItemServiceImpl(itemRepository, categoryRepository, sizeRepository);
        this.itemView = new ItemView(itemService, categoryService, sizeService);
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
            System.out.println("3. Manage Categories");
            System.out.println("4. Manage Sizes");
            System.out.println("5. Manage Events");
            System.out.println("6. Manage Items");
            System.out.println("0. Exit");

            int choice = ConsoleInput.readInt("Choice: ");
            switch (choice) {
                case 1 -> userView.handleMenu(null);
                case 2 -> customerView.handleMenu();
                case 3 -> categoryView.handleMenu();
                case 4 -> sizeView.handleMenu();
                case 5 -> eventView.handleMenu();
                case 6 -> itemView.handleMenu();
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