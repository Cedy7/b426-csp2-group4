package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.Customer;
import com.joysistvi.ribbons.service.CustomerService;

import java.util.List;

public class CustomerView {

    private final CustomerService customerService;

    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void handleMenu() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE CUSTOMERS ===");
            System.out.println("1. View All Active Customers");
            System.out.println("2. Add Customer");
            System.out.println("3. Update Customer");
            System.out.println("4. Search Customers");
            System.out.println("5. Archive Customer");
            System.out.println("6. Restore Archived Customer");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllCustomers();
                case 2 -> promptCreateCustomer();
                case 3 -> promptUpdateCustomer();
                case 4 -> promptSearchCustomers();
                case 5 -> promptArchiveCustomer();
                case 6 -> promptRestoreCustomer();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllCustomers() {
        List<Customer> customers = customerService.getAllActiveCustomers();

        System.out.println("\n----- Customers -----");
        printCustomers(customers);
    }

    public void promptCreateCustomer() {
        System.out.println("\n----- Add Customer -----");
        System.out.println("Note: Email is optional. Press Enter to skip.");
        String firstName = ConsoleInput.readString("Enter first name: ");
        String lastName = ConsoleInput.readString("Enter last name: ");
        String phoneNumber = ConsoleInput.readString("Enter phone number (09XXXXXXXXX): ");
        String email = ConsoleInput.readString("Enter email (optional): ");
        String address = ConsoleInput.readString("Enter address: ");

        boolean created = customerService.createCustomer(firstName, lastName, phoneNumber, email, address);

        if (created) {
            System.out.println("\nCustomer '" + firstName.trim() + " " + lastName.trim() + "' created successfully!");
        } else {
            System.out.println("Failed to create customer.");
        }
    }

    public void promptUpdateCustomer() {
        displayAllCustomers();
        System.out.println("\n----- Update Customer -----");
        int id = ConsoleInput.readInt("Enter customer ID to update: ");

        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        System.out.println("Note: Press Enter without typing anything to keep the current value.");
        System.out.println("      Type '-' to clear email.");
        String firstName = ConsoleInput.readString("Enter new first name [" + customer.getFirstName() + "]: ");
        String lastName = ConsoleInput.readString("Enter new last name [" + customer.getLastName() + "]: ");
        String phoneNumber = ConsoleInput.readString("Enter new phone number [" + customer.getPhoneNumber() + "]: ");
        String email = ConsoleInput.readString("Enter new email [" + displayValue(customer.getEmail()) + "]: ");
        String address = ConsoleInput.readString("Enter new address [" + displayValue(customer.getAddress()) + "]: ");

        if (firstName.trim().isEmpty()) {
            firstName = customer.getFirstName();
        }

        if (lastName.trim().isEmpty()) {
            lastName = customer.getLastName();
        }

        if (phoneNumber.trim().isEmpty()) {
            phoneNumber = customer.getPhoneNumber();
        }

        if (email.trim().isEmpty()) {
            email = customer.getEmail();
        } else if (email.trim().equals("-")) {
            email = null;
        }

        if (address.trim().isEmpty()) {
            address = customer.getAddress();
        }

        boolean updated = customerService.updateCustomer(id, firstName, lastName, phoneNumber, email, address);

        if (updated) {
            System.out.println("\nCustomer ID " + id + " updated successfully!");
            displayAllCustomers();
        } else {
            System.out.println("Failed to update customer.");
        }
    }

    public void promptSearchCustomers() {
        System.out.println("\n----- Search Customers -----");
        String keyword = ConsoleInput.readString("Enter name or phone number: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            customerService.searchCustomers(keyword);
            return;
        }

        List<Customer> customers = customerService.searchCustomers(keyword);

        System.out.println("\n----- Search Customers (Keyword: '" + keyword.trim() + "') -----");
        printCustomers(customers);
    }

    public void promptArchiveCustomer() {
        displayAllCustomers();
        System.out.println("\n----- Archive Customer -----");
        int id = ConsoleInput.readInt("Enter customer ID to archive: ");

        boolean archived = customerService.archiveCustomer(id);

        if (archived) {
            System.out.println("\nCustomer ID " + id + " archived successfully!");
            displayAllCustomers();
        } else {
            System.out.println("Failed to archive customer.");
        }
    }

    public void promptRestoreCustomer() {
        System.out.println("\n----- Restore Customer -----");

        List<Customer> archivedCustomers = customerService.getAllArchivedCustomers();

        if (archivedCustomers.isEmpty()) {
            System.out.println("No archived customers found.");
            return;
        }

        System.out.println("\n----- Archived Customers -----");
        printCustomers(archivedCustomers);

        int id = ConsoleInput.readInt("Enter customer ID to restore: ");

        boolean restored = customerService.restoreCustomer(id);

        if (restored) {
            System.out.println("\nCustomer ID " + id + " restored successfully!");
            displayAllCustomers();
        } else {
            System.out.println("Failed to restore customer.");
        }
    }

    private String displayValue(String value) {
        return value == null ? "none" : value;
    }

    private void printCustomers(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        String border = ConsoleInput.border(5, 25, 11, 25, 30);

        System.out.println(border);
        System.out.printf("| %-5s | %-25s | %-11s | %-25s | %-30s |%n", "ID", "Name", "Phone", "Email", "Address");
        System.out.println(border);

        for (Customer customer : customers) {
            System.out.printf("| %-5d | %-25s | %-11s | %-25s | %-30s |%n",
                    customer.getId(),
                    ConsoleInput.truncate(customer.getFullName(), 25),
                    customer.getPhoneNumber(),
                    ConsoleInput.truncate(customer.getEmail(), 25),
                    ConsoleInput.truncate(customer.getAddress(), 30));
        }

        System.out.println(border);
    }
}