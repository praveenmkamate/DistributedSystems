package com.ds.avengers.dto;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Getter
@Table(name="restaurant_menu_view", schema = "food_app")
public class RestaurantMenu {
    @Id
    private long id;
    private long restaurant_id;
    private String restaurant_name;
    private long menu_id;
    private long category_id;
    private String category_name;
    private String menu_name;
    private String item_name;
    private long price;
}
