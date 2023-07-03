package com.ds.avengers.repo;

import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.dto.FoodOrder;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IFoodOrderRepo extends ReadOnlyRepo<FoodOrder, Long>{
    @Query("select f FROM FoodOrder f WHERE f.customer_id = ?1")
    List<FoodOrder> getOrdersByCustomerId(Integer customerId);
}
