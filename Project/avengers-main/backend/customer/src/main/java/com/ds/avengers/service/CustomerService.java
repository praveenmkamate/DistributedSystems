package com.ds.avengers.service;

import com.ds.avengers.dto.Customer;
import com.ds.avengers.dto.CustomerOrderInformation;
import com.ds.avengers.dto.RestaurantMenu;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface CustomerService {

    ResponseEntity<List<Customer>> getAllCustomers();
    ResponseEntity<Customer> addCustomer(Customer customer);
    ResponseEntity updateCustomer(Customer customer);
    ResponseEntity createOrder(CustomerOrderInformation customerOrderInformation);

    ResponseEntity getOrders(Integer customerID);
    ResponseEntity<List<RestaurantMenu>> getAllMenuItems();
}
