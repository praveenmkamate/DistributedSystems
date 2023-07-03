package com.ds.avengers.repo;

import com.ds.avengers.dto.MenuItems;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IMenuItemsRepo extends JpaRepository<MenuItems, Long> {

    @Query("select m from MenuItems m where m.menu_id=?1")
    List<MenuItems> getMenuItems(Long menu_id);
}
