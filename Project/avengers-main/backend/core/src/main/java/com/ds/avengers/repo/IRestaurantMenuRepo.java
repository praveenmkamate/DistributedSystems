package com.ds.avengers.repo;

import com.ds.avengers.dto.CustomerOrder;
import com.ds.avengers.dto.RestaurantMenu;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface IRestaurantMenuRepo extends ReadOnlyRepo<RestaurantMenu, Long> {

    @Query("FROM RestaurantMenu rm WHERE rm.restaurant_id = ?1")
    List<RestaurantMenu> getItemsByRestaurant(Integer restaurantId);
}
