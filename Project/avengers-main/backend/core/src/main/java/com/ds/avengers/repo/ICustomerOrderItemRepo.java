package com.ds.avengers.repo;


import com.ds.avengers.dto.CustomerOrderItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerOrderItemRepo extends JpaRepository<CustomerOrderItem, Long> {
}
