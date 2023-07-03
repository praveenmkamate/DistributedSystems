package com.ds.avengers.repo;

import com.ds.avengers.dto.Restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRestaurantRepo extends JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r where r.status='ACTIVE'")
    List<Restaurant> activeRestaurants();
}
