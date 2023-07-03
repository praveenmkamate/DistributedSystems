package com.ds.avengers.controller;

import com.ds.avengers.dto.Customer;
import com.ds.avengers.dto.CustomerOrderInformation;
import com.ds.avengers.dto.RestaurantMenu;
import com.ds.avengers.service.CustomerServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer/api/v1")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/customer")
    public ResponseEntity addCustomer(@RequestBody Customer customer) {
        return customerService.addCustomer(customer);
    }

    @PutMapping("/customer")
    public ResponseEntity updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @GetMapping("/menuitems")
    public ResponseEntity<List<RestaurantMenu>> getAllMenuItems() {
        return customerService.getAllMenuItems();
    }

    @GetMapping("/orders/{customerID}")
    public ResponseEntity getOrders(@PathVariable("customerID") Integer customerID) {
        return customerService.getOrders(customerID);
    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@RequestBody CustomerOrderInformation customerOrderInformation) {
        return customerService.createOrder(customerOrderInformation);
    }

}
