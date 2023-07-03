package com.ds.avengers.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "menu", schema = "food_app")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer restaurant_id;
    private String menu_name;

    public Menu(Long id, Integer restaurant_id, String menu_name) {
        this.id = id;
        this.restaurant_id = restaurant_id;
        this.menu_name = menu_name;
    }

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
