package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Customer;

import java.util.List;

public interface CustomerRepository {

    List<Customer> findAllActive();

    List<Customer> findAllArchived();

    Customer findById(int id);

    Customer findByPhoneNumber(String phoneNumber);

    Customer findByEmail(String email);

    boolean save(Customer customer);

    boolean update(Customer customer);


    boolean archiveById(int id);

    boolean restoreById(int id);

    List<Customer> searchByNameOrPhone(String keyword);
}