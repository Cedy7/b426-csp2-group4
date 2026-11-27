package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Customer;
import com.joysistvi.ribbons.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllActiveCustomers() {
        return customerRepository.findAllActive();
    }

    @Override
    public List<Customer> getAllArchivedCustomers() {
        return customerRepository.findAllArchived();
    }

    @Override
    public Customer getCustomerById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return customerRepository.findById(id);
    }

    @Override
    public boolean createCustomer(String firstName, String lastName, String phoneNumber, String email, String address) {
        if (!isValidCustomer(firstName, lastName, phoneNumber, email, address)) {
            return false;
        }

        String cleanEmail = toNullIfBlank(email);

        if (customerRepository.findByPhoneNumber(phoneNumber.trim()) != null) {
            System.out.println("\nValidation Error: Phone number already exists.");
            return false;
        }

        if (cleanEmail != null && customerRepository.findByEmail(cleanEmail) != null) {
            System.out.println("\nValidation Error: Email already exists.");
            return false;
        }

        Customer customer = new Customer(firstName.trim(), lastName.trim(), phoneNumber.trim(), cleanEmail, address.trim());
        return customerRepository.save(customer);
    }

    @Override
    public boolean updateCustomer(int id, String firstName, String lastName, String phoneNumber, String email, String address) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (!isValidCustomer(firstName, lastName, phoneNumber, email, address)) {
            return false;
        }

        Customer existing = customerRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Customer not found.");
            return false;
        }

        String cleanEmail = toNullIfBlank(email);

        Customer samePhone = customerRepository.findByPhoneNumber(phoneNumber.trim());
        if (samePhone != null && samePhone.getId() != id) {
            System.out.println("\nValidation Error: Phone number already exists.");
            return false;
        }

        if (cleanEmail != null) {
            Customer sameEmail = customerRepository.findByEmail(cleanEmail);
            if (sameEmail != null && sameEmail.getId() != id) {
                System.out.println("\nValidation Error: Email already exists.");
                return false;
            }
        }

        existing.setFirstName(firstName.trim());
        existing.setLastName(lastName.trim());
        existing.setPhoneNumber(phoneNumber.trim());
        existing.setEmail(cleanEmail);
        existing.setAddress(address.trim());
        return customerRepository.update(existing);
    }

    @Override
    public boolean archiveCustomer(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return customerRepository.archiveById(id);
    }

    @Override
    public boolean restoreCustomer(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return customerRepository.restoreById(id);
    }

    @Override
    public List<Customer> searchCustomers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("\nValidation Error: Search keyword is required.");
            return new ArrayList<>();
        }

        return customerRepository.searchByNameOrPhone(keyword.trim());
    }

    private boolean isValidCustomer(String firstName, String lastName, String phoneNumber, String email, String address) {
        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("\nValidation Error: First name is required.");
            return false;
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("\nValidation Error: Last name is required.");
            return false;
        }

        if (!isValidPhoneNumber(phoneNumber)) {
            System.out.println("\nValidation Error: Phone number must be 11 digits and start with 09.");
            return false;
        }

        if (toNullIfBlank(email) != null && !isValidEmail(email)) {
            System.out.println("\nValidation Error: Invalid email format.");
            return false;
        }

        if (address == null || address.trim().isEmpty()) {
            System.out.println("\nValidation Error: Address is required.");
            return false;
        }

        return true;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.trim().matches("09\\d{9}");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private String toNullIfBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}