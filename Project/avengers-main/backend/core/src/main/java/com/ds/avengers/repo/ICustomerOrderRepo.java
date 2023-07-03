package com.ds.avengers.repo;

import com.ds.avengers.dto.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICustomerOrderRepo extends JpaRepository<CustomerOrder, String> {

    @Query("select c FROM CustomerOrder c WHERE c.restaurant_id = ?1")
    List<CustomerOrder> getOrdersByRestaurant(Integer restaurantId);

    @Query("select c FROM CustomerOrder c WHERE c.customer_id = ?1")
    List<CustomerOrder> getOrdersByCustomer(Integer customerID);

    @Query("select c FROM CustomerOrder c WHERE c.id = ?1")
    CustomerOrder getOrdersByOrderId(String orderID);
}
