package com.ds.avengers.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "food_order", schema = "food_app")
@Getter
@Setter
public class CustomerOrder implements Serializable {

    @Id
    private String id;

    private Integer restaurant_id;
    private Integer customer_id;
    private Integer delivery_agent_id;
    private String order_time;
    private String delivery_time;
    private String delivery_address;
    private float order_price;
    private String order_status;

    public CustomerOrder(Integer restaurant_id, Integer customer_id,
             Integer delivery_agent_id, String order_time, String delivery_time,
             String delivery_address, Integer order_price, String order_status) {
        this.restaurant_id = restaurant_id;
        this.customer_id = customer_id;
        this.delivery_agent_id = delivery_agent_id;
        this.order_time = order_time;
        this.delivery_time = delivery_time;
        this.delivery_address = delivery_address;
        this.order_price = order_price;
        this.order_status = order_status;
    }

    public CustomerOrder() {
    }

    public CustomerOrder(CustomerOrder co) {
        this.restaurant_id = co.restaurant_id;
        this.customer_id = co.customer_id;
        this.delivery_address = co.delivery_address;
    }

    @Override
    public String toString() {
        return "{" +
                "id:'" + id + '\'' +
                ", restaurant_id:" + restaurant_id +
                ", customer_id:" + customer_id +
                ", delivery_agent_id:" + delivery_agent_id +
                ", order_time:'" + order_time + '\'' +
                ", delivery_time:'" + delivery_time + '\'' +
                ", delivery_address:'" + delivery_address + '\'' +
                ", order_price:" + order_price +
                ", order_status:'" + order_status + '\'' +
                '}';
    }
}
