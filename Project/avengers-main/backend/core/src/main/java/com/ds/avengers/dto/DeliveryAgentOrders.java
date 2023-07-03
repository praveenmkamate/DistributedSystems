package com.ds.avengers.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Entity
@Immutable
@Getter
@Table(name="delivery_agent_orders_view", schema = "food_app")
public class DeliveryAgentOrders {
    @Id
    private long id;
    private String restaurant;
    private String customer_name;
    private String delivery_time;
    private String delivery_address;
    private float order_price;
    private String order_status;
    private int delivery_agent_id;
}
