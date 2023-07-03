package com.ds.avengers.repo;

import com.ds.avengers.dto.Menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMenuRepo extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.restaurant_id = ?1")
    Menu getMenu(@Param("restaurantId") Integer restaurantId);
}
