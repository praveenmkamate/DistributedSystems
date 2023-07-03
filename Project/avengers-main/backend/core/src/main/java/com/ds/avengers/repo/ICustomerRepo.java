package com.ds.avengers.repo;

import com.ds.avengers.dto.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICustomerRepo extends JpaRepository<Customer, Long> {
    @Query("FROM Customer c WHERE name = ?1")
    Customer findByCustomerName(String name);

    @Modifying
    @Query("update Customer c set c.address = :address, c.phone = :phone where c.id = :id")
    void updateCustomer(@Param("address") String address, @Param("phone") String phone, @Param("id") Long id);
}
