package com.ds.avengers.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Getter
@Table(name="food_order_menu_item_view", schema = "food_app")
public class FoodOrderMenuItem {
    @Id
    private int id;
    private int item_id;
    private String item_name;
    private int order_id;
    private int count;
}
