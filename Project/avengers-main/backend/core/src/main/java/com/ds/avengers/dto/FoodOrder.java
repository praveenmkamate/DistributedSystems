package com.ds.avengers.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Getter
@Table(name="food_order_view", schema = "food_app")
public class FoodOrder {
    @Id
    private String id;
    private int restaurant_id;
    private String restaurant_name;
    private int customer_id;
    private String customer_name;
    private String order_time;
    private String delivery_time;
    private String delivery_address;
    private long order_price;
    private String order_status;
}
