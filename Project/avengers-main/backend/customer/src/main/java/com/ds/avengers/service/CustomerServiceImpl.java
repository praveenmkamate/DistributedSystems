package com.ds.avengers.service;

import com.ds.avengers.Constants;
import com.ds.avengers.dto.*;
import com.ds.avengers.repo.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * This is service class for all the end points in this service
 *
 **/
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private ICustomerRepo customerRepo;
    @Autowired
    private ICustomerOrderRepo customerOrderRepo;

    @Autowired
    private ICustomerOrderItemRepo customerOrderItemRepo;

    @Autowired
    private IFoodOrderRepo foodOrderRepo;

    @Autowired
    private IRestaurantMenuRepo restaurantMenuRepo;

    @Autowired
    private RMQService rmqService;

    @Override
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> listCustomers;
        try {
            listCustomers = customerRepo.findAll();
            return new ResponseEntity<>(listCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Customer> addCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Customer resCustomer = customerRepo.findByCustomerName(customer.getName());
            if (null == resCustomer) {
                customerRepo.save(customer);
            }
            resCustomer = customerRepo.findByCustomerName(customer.getName());
            return new ResponseEntity<>(resCustomer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity updateCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Customer resCustomer = customerRepo.findByCustomerName(customer.getName());
            if (null == resCustomer) {
                customerRepo.save(customer);
            } else {
                customerRepo.updateCustomer(customer.getAddress(), customer.getPhone(), resCustomer.getId());
            }
            resCustomer = customerRepo.findByCustomerName(customer.getName());
            return new ResponseEntity<>(resCustomer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public boolean validateCustomer(Customer customer) {
        boolean res = true;
        if (null == customer.getName() || null == customer.getAddress() || null == customer.getPhone()) {
            res = false;
        }
        return res;

    }

    public boolean validateCustomerOrder(CustomerOrderInformation customerOrderInformation) {
        boolean res = true;
        if (null != customerOrderInformation || null != customerOrderInformation.getCustomerOrder() || null != customerOrderInformation.getCustomerOrderItems()) {
            if (null != customerOrderInformation.getCustomerOrder().getCustomer_id() || null != customerOrderInformation.getCustomerOrder().getRestaurant_id() || null != customerOrderInformation.getCustomerOrder().getDelivery_address() || customerOrderInformation.getCustomerOrderItems().size() > 0) {
                for (CustomerOrderItem item :
                        customerOrderInformation.getCustomerOrderItems()) {
                    if (null == item.getCount() || null == item.getItem_id()) {
                        res = false;
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
     * This method validates and creates an order.
     **/
    @Override
    public ResponseEntity<CustomerOrder> createOrder(CustomerOrderInformation customerOrderInformation) {
        if (!validateCustomerOrder(customerOrderInformation)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        float totalPriceOfOrder = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        //OrderID is the current time stamp in milli seconds
        String order_id = String.valueOf(System.currentTimeMillis());
        try {
            for (CustomerOrderItem item :
                    customerOrderInformation.getCustomerOrderItems()) {
                totalPriceOfOrder += (item.getItem_price() * item.getCount());
                item.setOrder_id(order_id);
            }
            CustomerOrder co = new CustomerOrder(customerOrderInformation.getCustomerOrder());
            co.setOrder_price(totalPriceOfOrder);
            co.setOrder_status(Constants.orderStatus.ORDERED.toString());
            co.setOrder_time(formatter.format(date));
            co.setId(order_id);

            customerOrderRepo.save(co);
            customerOrderItemRepo.saveAll(customerOrderInformation.getCustomerOrderItems());
            CustomerOrder savedCustomerOrder = customerOrderRepo.getOrdersByOrderId(order_id);
            rmqService.sendUpdateToRestaurantService(savedCustomerOrder);
            return new ResponseEntity<>(savedCustomerOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<FoodOrder>> getOrders(Integer customerID) {
        if (null == customerID) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<FoodOrder> foodOrders = foodOrderRepo.getOrdersByCustomerId(customerID);
            return new ResponseEntity<>(foodOrders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<RestaurantMenu>> getAllMenuItems() {
        List<RestaurantMenu> rm;
        try {
            rm = restaurantMenuRepo.findAll();
            return new ResponseEntity<>(rm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
