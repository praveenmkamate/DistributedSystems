package com.ds.avengers.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "menu_item", schema = "food_app")
public class MenuItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer menu_id;
    private Integer category_id;
    private String item_name;
    private Float price;
    private String status;

    public MenuItems(Long id, Integer menu_id, Integer category_id, String item_name,
                     Float price, String status) {
        this.id = id;
        this.menu_id = menu_id;
        this.category_id = category_id;
        this.item_name = item_name;
        this.price = price;
        this.status = status;
    }

    public MenuItems() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
