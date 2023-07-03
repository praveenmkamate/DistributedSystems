package com.ds.avengers.repo;

import com.ds.avengers.dto.OrderDetails;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDetailsRepo extends JpaRepository<OrderDetails, Long> {

}
