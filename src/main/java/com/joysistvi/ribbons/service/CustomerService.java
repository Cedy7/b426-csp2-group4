package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllActiveCustomers();

    List<Customer> getAllArchivedCustomers();

    Customer getCustomerById(int id);

    boolean createCustomer(String firstName, String lastName, String phoneNumber, String email, String address);

    boolean updateCustomer(int id, String firstName, String lastName, String phoneNumber, String email, String address);

    boolean archiveCustomer(int id);

    boolean restoreCustomer(int id);

    List<Customer> searchCustomers(String keyword);
}